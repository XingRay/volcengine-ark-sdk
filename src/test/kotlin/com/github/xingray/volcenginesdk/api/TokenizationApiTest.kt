package com.github.xingray.volcenginesdk.api

import com.github.xingray.volcenginesdk.model.chat.ChatMessage
import com.github.xingray.volcenginesdk.model.chat.ChatMessageRole
import com.github.xingray.volcenginesdk.model.tokenization.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.*

/**
 * TokenizationApi 集成测试。
 *
 * 使用真实 API 进行测试，需要设置环境变量 ARK_API_KEY。
 */
class TokenizationApiTest {

    private val apiKey get() = TestConfig.requireApiKey()
    private val model = "doubao-seed-1.6-250615"
    private val client = TestConfig.createClient()
    private val api = TokenizationApi(client)

    @AfterTest
    fun tearDown() {
        client.close()
    }

    // ========== createTokenization ==========

    @Test
    fun `createTokenization with text should return token count`() = runTest {
        val inputText = "你好世界"
        val result = api.createTokenization(
            apiKey,
            TokenizationRequest(model = model, text = listOf(inputText)),
        )

        assertTrue(result.data.isNotEmpty(), "data 不应为空")
        assertTrue(result.data[0].totalTokens > 0, "totalTokens 应大于 0")
        assertEquals(0, result.data[0].index)

        println("========== createTokenization (单文本) ==========")
        println("输入文本: \"$inputText\"")
        println("index: ${result.data[0].index}")
        println("totalTokens: ${result.data[0].totalTokens}")
    }

    @Test
    fun `createTokenization with messages should return token count`() = runTest {
        val result = api.createTokenization(
            apiKey,
            TokenizationRequest(
                model = model,
                messages = listOf(
                    ChatMessage(role = ChatMessageRole.SYSTEM, content = JsonPrimitive("你是一个助手")),
                    ChatMessage(role = ChatMessageRole.USER, content = JsonPrimitive("你好")),
                ),
            ),
        )

        assertTrue(result.data.isNotEmpty(), "data 不应为空")
        assertTrue(result.data[0].totalTokens > 0, "totalTokens 应大于 0")

        println("========== createTokenization (消息列表) ==========")
        println("输入消息: [SYSTEM: \"你是一个助手\", USER: \"你好\"]")
        println("index: ${result.data[0].index}")
        println("totalTokens: ${result.data[0].totalTokens}")
    }

    @Test
    fun `createTokenization with multiple texts should return multiple results`() = runTest {
        val inputs = listOf("你好", "世界", "这是一段较长的测试文本")
        val result = api.createTokenization(
            apiKey,
            TokenizationRequest(model = model, text = inputs),
        )

        assertTrue(result.data.size >= 2, "应返回多个 tokenization 结果")
        result.data.forEach { tokenization ->
            assertTrue(tokenization.totalTokens > 0, "每个 totalTokens 应大于 0")
        }

        println("========== createTokenization (多文本) ==========")
        result.data.forEach { tokenization ->
            val inputText = if (tokenization.index < inputs.size) inputs[tokenization.index] else "?"
            println("  \"$inputText\" -> index=${tokenization.index}, totalTokens=${tokenization.totalTokens}")
        }
    }
}
