package com.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.model.chat.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlin.test.*

/**
 * ChatCompletionApi 集成测试。
 *
 * 使用真实 API 进行测试，需要设置环境变量 ARK_API_KEY。
 */
class ChatCompletionApiTest {

    private val apiKey get() = TestConfig.requireApiKey()
    private val model = "doubao-seed-1.6-250615"
    private val client = TestConfig.createClient()
    private val api = ChatCompletionApi(client)

    @AfterTest
    fun tearDown() {
        client.close()
    }

    // ========== createChatCompletion ==========

    @Test
    fun `createChatCompletion should return valid result`() = runTest {
        val request = ChatCompletionRequest(
            model = model,
            messages = listOf(
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("请回复：你好"))
            ),
            maxTokens = 50,
        )
        val result = api.createChatCompletion(apiKey, request)

        assertNotNull(result.id, "id 不应为空")
        assertTrue(result.choices.isNotEmpty(), "choices 不应为空")
        assertNotNull(result.choices[0].message, "message 不应为空")
        assertEquals(ChatMessageRole.ASSISTANT, result.choices[0].message?.role)
        assertNotNull(result.choices[0].finishReason, "finishReason 不应为空")
        assertNotNull(result.usage, "usage 不应为空")
        assertTrue(result.usage.totalTokens > 0, "totalTokens 应大于 0")

        println("========== createChatCompletion ==========")
        println("id: ${result.id}")
        println("model: ${result.model}")
        println("created: ${result.created}")
        println("finishReason: ${result.choices[0].finishReason}")
        println("回复内容: ${result.choices[0].message?.content}")
        println("usage: promptTokens=${result.usage.promptTokens}, completionTokens=${result.usage.completionTokens}, totalTokens=${result.usage.totalTokens}")
    }

    @Test
    fun `createChatCompletion with multi-turn messages should return valid result`() = runTest {
        val request = ChatCompletionRequest(
            model = model,
            messages = listOf(
                ChatMessage(role = ChatMessageRole.SYSTEM, content = JsonPrimitive("你是一个有用的助手")),
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("1+1等于几？")),
                ChatMessage(role = ChatMessageRole.ASSISTANT, content = JsonPrimitive("1+1等于2。")),
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("再加1呢？")),
            ),
            maxTokens = 50,
        )
        val result = api.createChatCompletion(apiKey, request)

        assertNotNull(result.id)
        assertTrue(result.choices.isNotEmpty())

        println("========== createChatCompletion (多轮对话) ==========")
        println("id: ${result.id}")
        println("回复内容: ${result.choices[0].message?.content}")
        println("finishReason: ${result.choices[0].finishReason}")
        println("usage: promptTokens=${result.usage?.promptTokens}, completionTokens=${result.usage?.completionTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    // ========== streamChatCompletion ==========

    @Test
    fun `streamChatCompletion should return Flow of chunks`() = runTest {
        val request = ChatCompletionRequest(
            model = model,
            messages = listOf(
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("请回复：你好"))
            ),
            maxTokens = 50,
        )
        val chunks = api.streamChatCompletion(apiKey, request).toList()

        assertTrue(chunks.isNotEmpty(), "chunks 不应为空")
        assertNotNull(chunks[0].id, "第一个 chunk 的 id 不应为空")
        assertTrue(chunks.any { it.choices.isNotEmpty() }, "至少有一个 chunk 包含 choices")

        println("========== streamChatCompletion ==========")
        println("id: ${chunks[0].id}")
        println("共 ${chunks.size} 个 chunks")
        val fullContent = buildString {
            chunks.forEach { chunk ->
                chunk.choices.firstOrNull()?.delta?.content?.let {
                    val text = if (it is JsonPrimitive) it.content else it.toString()
                    append(text)
                }
            }
        }
        println("拼接完整回复: $fullContent")
        val lastUsage = chunks.lastOrNull { it.usage != null }?.usage
        if (lastUsage != null) {
            println("usage: promptTokens=${lastUsage.promptTokens}, completionTokens=${lastUsage.completionTokens}, totalTokens=${lastUsage.totalTokens}")
        }
    }

    // ========== createChatCompletionWithThinking ==========

    @Test
    fun `createChatCompletionWithThinking should return result with reasoning content`() = runTest {
        val result = api.createChatCompletionWithThinking(
            apiKey = apiKey,
            model = "doubao-seed-1.6-thinking-250615",
            messages = listOf(
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("9.8和9.11哪个大？"))
            ),
            maxTokens = 500,
        )

        assertNotNull(result.id)
        assertTrue(result.choices.isNotEmpty())
        assertNotNull(result.choices[0].message)

        println("========== createChatCompletionWithThinking ==========")
        println("id: ${result.id}")
        println("model: ${result.model}")
        val msg = result.choices[0].message!!
        println("推理过程 (reasoningContent): ${msg.reasoningContent ?: "(无)"}")
        println("最终回复: ${msg.content}")
        println("finishReason: ${result.choices[0].finishReason}")
        println("usage: promptTokens=${result.usage?.promptTokens}, completionTokens=${result.usage?.completionTokens}, totalTokens=${result.usage?.totalTokens}")
        result.usage?.completionTokensDetails?.let {
            println("  reasoningTokens: ${it.reasoningTokens}")
        }
    }

    // ========== streamChatCompletionWithThinking ==========

    @Test
    fun `streamChatCompletionWithThinking should return Flow of chunks`() = runTest {
        val chunks = api.streamChatCompletionWithThinking(
            apiKey = apiKey,
            model = "doubao-seed-1.6-thinking-250615",
            messages = listOf(
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("1+1=?"))
            ),
            maxTokens = 200,
        ).toList()

        assertTrue(chunks.isNotEmpty(), "chunks 不应为空")

        println("========== streamChatCompletionWithThinking ==========")
        println("id: ${chunks[0].id}")
        println("共 ${chunks.size} 个 chunks")
        val reasoningContent = buildString {
            chunks.forEach { chunk ->
                chunk.choices.firstOrNull()?.delta?.reasoningContent?.let { append(it) }
            }
        }
        val replyContent = buildString {
            chunks.forEach { chunk ->
                chunk.choices.firstOrNull()?.delta?.content?.let {
                    val text = if (it is JsonPrimitive) it.content else it.toString()
                    append(text)
                }
            }
        }
        println("推理过程: ${reasoningContent.ifEmpty { "(无)" }}")
        println("最终回复: ${replyContent.ifEmpty { "(无)" }}")
    }

    // ========== createPrefillChatCompletion ==========

    @Test
    fun `createPrefillChatCompletion should return continuation result`() = runTest {
        val result = api.createPrefillChatCompletion(
            apiKey = apiKey,
            model = model,
            messages = listOf(
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("用一句话介绍北京")),
                ChatMessage(role = ChatMessageRole.ASSISTANT, content = JsonPrimitive("北京是")),
            ),
            maxTokens = 100,
        )

        assertNotNull(result.id)
        assertTrue(result.choices.isNotEmpty())

        println("========== createPrefillChatCompletion ==========")
        println("id: ${result.id}")
        println("续写结果: 北京是${result.choices[0].message?.content}")
        println("finishReason: ${result.choices[0].finishReason}")
        println("usage: promptTokens=${result.usage?.promptTokens}, completionTokens=${result.usage?.completionTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    // ========== createChatCompletionWithTools ==========

    @Test
    fun `createChatCompletionWithTools should return result with tool calls`() = runTest {
        val tools = listOf(
            ChatTool(
                type = "function",
                function = ChatFunction(
                    name = "get_current_weather",
                    description = "获取指定城市的当前天气",
                    parameters = buildJsonObject {
                        put("type", JsonPrimitive("object"))
                        put("properties", buildJsonObject {
                            put("city", buildJsonObject {
                                put("type", JsonPrimitive("string"))
                                put("description", JsonPrimitive("城市名称"))
                            })
                        })
                        put("required", buildJsonArray {
                            add(JsonPrimitive("city"))
                        })
                    },
                ),
            ),
        )

        val result = api.createChatCompletionWithTools(
            apiKey = apiKey,
            model = model,
            messages = listOf(
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("北京今天天气怎么样？"))
            ),
            tools = tools,
            maxTokens = 200,
        )

        assertNotNull(result.id)
        assertTrue(result.choices.isNotEmpty())

        println("========== createChatCompletionWithTools ==========")
        println("id: ${result.id}")
        println("finishReason: ${result.choices[0].finishReason}")
        val msg = result.choices[0].message!!
        if (msg.toolCalls != null && msg.toolCalls.isNotEmpty()) {
            println("工具调用:")
            msg.toolCalls.forEach { toolCall ->
                println("  - id=${toolCall.id}, type=${toolCall.type}")
                println("    函数名: ${toolCall.function?.name}")
                println("    参数: ${toolCall.function?.arguments}")
            }
        } else {
            println("回复内容 (未触发工具调用): ${msg.content}")
        }
        println("usage: promptTokens=${result.usage?.promptTokens}, completionTokens=${result.usage?.completionTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    // ========== streamChatCompletionWithTools ==========

    @Test
    fun `streamChatCompletionWithTools should return Flow of chunks`() = runTest {
        val tools = listOf(
            ChatTool(
                type = "function",
                function = ChatFunction(
                    name = "get_time",
                    description = "获取当前时间",
                ),
            ),
        )

        val chunks = api.streamChatCompletionWithTools(
            apiKey = apiKey,
            model = model,
            messages = listOf(
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("现在几点了？"))
            ),
            tools = tools,
            maxTokens = 200,
        ).toList()

        assertTrue(chunks.isNotEmpty(), "chunks 不应为空")

        println("========== streamChatCompletionWithTools ==========")
        println("id: ${chunks[0].id}")
        println("共 ${chunks.size} 个 chunks")
        val functionName = buildString {
            chunks.forEach { chunk ->
                chunk.choices.firstOrNull()?.delta?.toolCalls?.firstOrNull()?.function?.name?.let { append(it) }
            }
        }
        val functionArgs = buildString {
            chunks.forEach { chunk ->
                chunk.choices.firstOrNull()?.delta?.toolCalls?.firstOrNull()?.function?.arguments?.let { append(it) }
            }
        }
        if (functionName.isNotEmpty()) {
            println("工具调用函数名: $functionName")
            println("工具调用参数: $functionArgs")
        }
        val lastFinishReason = chunks.lastOrNull { it.choices.firstOrNull()?.finishReason != null }?.choices?.firstOrNull()?.finishReason
        println("finishReason: $lastFinishReason")
    }

    // ========== createChatCompletionWithJsonOutput ==========

    @Test
    fun `createChatCompletionWithJsonOutput should return JSON formatted result`() = runTest {
        val result = api.createChatCompletionWithJsonOutput(
            apiKey = apiKey,
            model = model,
            messages = listOf(
                ChatMessage(role = ChatMessageRole.SYSTEM, content = JsonPrimitive("以JSON格式回复")),
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("给我一个包含name和age字段的JSON")),
            ),
            maxTokens = 100,
        )

        assertNotNull(result.id)
        assertTrue(result.choices.isNotEmpty())
        assertNotNull(result.choices[0].message?.content, "JSON 输出内容不应为空")

        println("========== createChatCompletionWithJsonOutput ==========")
        println("id: ${result.id}")
        println("JSON 输出: ${result.choices[0].message?.content}")
        println("finishReason: ${result.choices[0].finishReason}")
        println("usage: promptTokens=${result.usage?.promptTokens}, completionTokens=${result.usage?.completionTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    @Test
    fun `createChatCompletionWithJsonOutput with schema should return structured result`() = runTest {
        val schema = JsonSchemaParam(
            name = "person",
            schema = buildJsonObject {
                put("type", JsonPrimitive("object"))
                put("properties", buildJsonObject {
                    put("name", buildJsonObject { put("type", JsonPrimitive("string")) })
                    put("age", buildJsonObject { put("type", JsonPrimitive("integer")) })
                })
                put("required", buildJsonArray {
                    add(JsonPrimitive("name"))
                    add(JsonPrimitive("age"))
                })
            },
        )

        val result = api.createChatCompletionWithJsonOutput(
            apiKey = apiKey,
            model = model,
            messages = listOf(
                ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("生成一个人的信息")),
            ),
            jsonSchema = schema,
            maxTokens = 100,
        )

        assertNotNull(result.id)
        assertTrue(result.choices.isNotEmpty())

        println("========== createChatCompletionWithJsonOutput (with schema) ==========")
        println("id: ${result.id}")
        println("JSON Schema 输出: ${result.choices[0].message?.content}")
        println("finishReason: ${result.choices[0].finishReason}")
        println("usage: promptTokens=${result.usage?.promptTokens}, completionTokens=${result.usage?.completionTokens}, totalTokens=${result.usage?.totalTokens}")
    }
}
