package com.github.xingray.volcenginesdk.api

import com.github.xingray.volcenginesdk.model.chat.*
import com.github.xingray.volcenginesdk.model.context.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.*

/**
 * ContextApi 集成测试。
 *
 * 使用真实 API 进行测试，需要设置环境变量 ARK_API_KEY。
 */
class ContextApiTest {

    private val apiKey get() = TestConfig.requireApiKey()
    private val model = "doubao-seed-1.6-250615"
    private val client = TestConfig.createClient()
    private val api = ContextApi(client)

    @AfterTest
    fun tearDown() {
        client.close()
    }

    // ========== createContext ==========

    @Test
    fun `createContext should return context result`() = runTest {
        val result = api.createContext(
            apiKey,
            CreateContextRequest(
                model = model,
                mode = "session",
                messages = listOf(
                    ChatMessage(role = ChatMessageRole.SYSTEM, content = JsonPrimitive("你是一个有用的助手，请用中文回答。")),
                ),
                ttl = 3600,
            ),
        )

        assertNotNull(result.id, "context id 不应为空")
        assertNotNull(result.mode, "mode 不应为空")

        println("========== createContext ==========")
        println("contextId: ${result.id}")
        println("mode: ${result.mode}")
        println("ttl: ${result.ttl}")
    }

    // ========== createContextChatCompletion ==========

    @Test
    fun `createContextChatCompletion should return chat result`() = runTest {
        // 先创建上下文
        val contextResult = api.createContext(
            apiKey,
            CreateContextRequest(
                model = model,
                mode = "session",
                messages = listOf(
                    ChatMessage(role = ChatMessageRole.SYSTEM, content = JsonPrimitive("你是一个数学助手")),
                ),
                ttl = 300,
            ),
        )
        assertNotNull(contextResult.id)

        // 使用上下文进行对话
        val chatResult = api.createContextChatCompletion(
            apiKey,
            ChatCompletionRequest(
                model = model,
                messages = listOf(
                    ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("1+1等于几？")),
                ),
                maxTokens = 50,
            ),
        )

        assertNotNull(chatResult.id)
        assertTrue(chatResult.choices.isNotEmpty(), "choices 不应为空")
        assertNotNull(chatResult.choices[0].message)

        println("========== createContextChatCompletion ==========")
        println("contextId: ${contextResult.id}")
        println("chatId: ${chatResult.id}")
        println("model: ${chatResult.model}")
        println("回复内容: ${chatResult.choices[0].message?.content}")
        println("finishReason: ${chatResult.choices[0].finishReason}")
        println("usage: promptTokens=${chatResult.usage?.promptTokens}, completionTokens=${chatResult.usage?.completionTokens}, totalTokens=${chatResult.usage?.totalTokens}")
    }

    // ========== streamContextChatCompletion ==========

    @Test
    fun `streamContextChatCompletion should return Flow of chunks`() = runTest {
        val chunks = api.streamContextChatCompletion(
            apiKey,
            ChatCompletionRequest(
                model = model,
                messages = listOf(
                    ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("你好")),
                ),
                maxTokens = 50,
            ),
        ).toList()

        assertTrue(chunks.isNotEmpty(), "chunks 不应为空")
        assertNotNull(chunks[0].id, "第一个 chunk 的 id 不应为空")

        println("========== streamContextChatCompletion ==========")
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
}
