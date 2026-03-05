package com.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.model.bot.*
import io.github.xingray.volcenginesdk.model.chat.ChatMessage
import io.github.xingray.volcenginesdk.model.chat.ChatMessageRole
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.*

/**
 * BotChatApi 集成测试。
 *
 * 使用真实 API 进行测试，需要设置环境变量 ARK_API_KEY。
 *
 * 注意：Bot Chat 的 model 参数需要填写在火山引擎控制台创建的 Bot ID。
 * 请将下面的 BOT_MODEL 修改为你的 Bot ID。
 */
class BotChatApiTest {

    private val apiKey get() = TestConfig.requireApiKey()
    private val model = "bot-xxx"  // 请替换为你在火山引擎控制台创建的 Bot ID
    private val client = TestConfig.createClient()
    private val api = BotChatApi(client)

    @AfterTest
    fun tearDown() {
        client.close()
    }

    // ========== createBotChatCompletion ==========

    @Test
    fun `createBotChatCompletion should return bot response`() = runTest {
        val result = api.createBotChatCompletion(
            apiKey,
            BotChatCompletionRequest(
                model = model,
                messages = listOf(
                    ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("你好"))
                ),
                maxTokens = 100,
            ),
        )

        assertNotNull(result.id, "id 不应为空")
        assertTrue(result.choices.isNotEmpty(), "choices 不应为空")
        assertNotNull(result.choices[0].message, "message 不应为空")
        assertNotNull(result.usage, "usage 不应为空")

        println("========== createBotChatCompletion ==========")
        println("id: ${result.id}")
        println("model: ${result.model}")
        println("created: ${result.created}")
        println("Bot 回复: ${result.choices[0].message?.content}")
        println("finishReason: ${result.choices[0].finishReason}")
        println("usage: promptTokens=${result.usage.promptTokens}, completionTokens=${result.usage.completionTokens}, totalTokens=${result.usage.totalTokens}")
        if (result.references != null && result.references.isNotEmpty()) {
            println("引用来源 (共 ${result.references.size} 条):")
            result.references.forEachIndexed { index, ref ->
                println("  引用 #$index:")
                println("    title: ${ref.title ?: "(无)"}")
                println("    url: ${ref.url ?: "(无)"}")
                println("    content: ${ref.content?.take(100) ?: "(无)"}${if ((ref.content?.length ?: 0) > 100) "..." else ""}")
            }
        }
    }

    // ========== streamBotChatCompletion ==========

    @Test
    fun `streamBotChatCompletion should return Flow of chunks`() = runTest {
        val chunks = api.streamBotChatCompletion(
            apiKey,
            BotChatCompletionRequest(
                model = model,
                messages = listOf(
                    ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("你好"))
                ),
                maxTokens = 100,
            ),
        ).toList()

        assertTrue(chunks.isNotEmpty(), "chunks 不应为空")
        assertNotNull(chunks[0].id, "第一个 chunk 的 id 不应为空")

        println("========== streamBotChatCompletion ==========")
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
        // 检查最后一个 chunk 是否包含引用
        val lastChunkWithRefs = chunks.lastOrNull { it.references != null && it.references.isNotEmpty() }
        if (lastChunkWithRefs?.references != null) {
            println("引用来源 (共 ${lastChunkWithRefs.references.size} 条):")
            lastChunkWithRefs.references.forEachIndexed { index, ref ->
                println("  引用 #$index: title=${ref.title ?: "(无)"}, url=${ref.url ?: "(无)"}")
            }
        }
        val lastUsage = chunks.lastOrNull { it.usage != null }?.usage
        if (lastUsage != null) {
            println("usage: promptTokens=${lastUsage.promptTokens}, completionTokens=${lastUsage.completionTokens}, totalTokens=${lastUsage.totalTokens}")
        }
    }
}
