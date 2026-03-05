package com.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.model.embedding.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

/**
 * EmbeddingApi 集成测试。
 *
 * 使用真实 API 进行测试，需要设置环境变量 ARK_API_KEY。
 */
class EmbeddingApiTest {

    private val apiKey get() = TestConfig.requireApiKey()
    private val embeddingModel = "doubao-embedding"
    private val multimodalEmbeddingModel = "doubao-embedding-vision"
    private val client = TestConfig.createClient()
    private val api = EmbeddingApi(client)

    @AfterTest
    fun tearDown() {
        client.close()
    }

    // ========== createEmbeddings ==========

    @Test
    fun `createEmbeddings should return embedding vectors`() = runTest {
        val result = api.createEmbeddings(
            apiKey,
            EmbeddingRequest(model = embeddingModel, input = listOf("你好世界")),
        )

        assertNotNull(result.objectType)
        assertTrue(result.data.isNotEmpty(), "data 不应为空")
        assertTrue(result.data[0].embedding.isNotEmpty(), "embedding 向量不应为空")
        assertEquals(0, result.data[0].index)
        assertNotNull(result.usage, "usage 不应为空")
        assertTrue(result.usage.totalTokens > 0)

        println("========== createEmbeddings ==========")
        println("model: ${result.model}")
        println("objectType: ${result.objectType}")
        println("向量维度: ${result.data[0].embedding.size}")
        println("向量前5维: ${result.data[0].embedding.take(5)}")
        println("usage: promptTokens=${result.usage.promptTokens}, totalTokens=${result.usage.totalTokens}")
    }

    // ========== createBatchEmbeddings ==========

    @Test
    fun `createBatchEmbeddings should return multiple embedding vectors`() = runTest {
        val inputs = listOf("你好", "世界", "测试文本")
        val result = api.createBatchEmbeddings(
            apiKey,
            EmbeddingRequest(model = embeddingModel, input = inputs),
        )

        assertTrue(result.data.isNotEmpty(), "data 不应为空")
        assertTrue(result.data.size >= 2, "应返回多个向量")
        result.data.forEach { embedding ->
            assertTrue(embedding.embedding.isNotEmpty(), "每个 embedding 向量不应为空")
        }

        println("========== createBatchEmbeddings ==========")
        println("model: ${result.model}")
        println("输入文本数: ${inputs.size}, 返回向量数: ${result.data.size}")
        result.data.forEach { embedding ->
            println("  - index=${embedding.index}, 维度=${embedding.embedding.size}, 前3维=${embedding.embedding.take(3)}")
        }
        println("usage: promptTokens=${result.usage?.promptTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    // ========== createMultimodalEmbeddings ==========

    @Test
    fun `createMultimodalEmbeddings should return embedding vectors`() = runTest {
        val result = api.createMultimodalEmbeddings(
            apiKey,
            MultimodalEmbeddingRequest(
                model = multimodalEmbeddingModel,
                input = listOf(
                    MultimodalEmbeddingInput(type = "text", text = "一只可爱的猫"),
                ),
            ),
        )

        assertTrue(result.data.isNotEmpty(), "data 不应为空")
        assertTrue(result.data[0].embedding.isNotEmpty(), "embedding 向量不应为空")

        println("========== createMultimodalEmbeddings ==========")
        println("model: ${result.model}")
        println("向量维度: ${result.data[0].embedding.size}")
        println("向量前5维: ${result.data[0].embedding.take(5)}")
        println("usage: promptTokens=${result.usage?.promptTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    // ========== createBatchMultimodalEmbeddings ==========

    @Test
    fun `createBatchMultimodalEmbeddings should return embedding vectors`() = runTest {
        val result = api.createBatchMultimodalEmbeddings(
            apiKey,
            MultimodalEmbeddingRequest(
                model = multimodalEmbeddingModel,
                input = listOf(
                    MultimodalEmbeddingInput(type = "text", text = "你好"),
                    MultimodalEmbeddingInput(type = "text", text = "世界"),
                ),
            ),
        )

        assertTrue(result.data.isNotEmpty(), "data 不应为空")

        println("========== createBatchMultimodalEmbeddings ==========")
        println("model: ${result.model}")
        println("返回向量数: ${result.data.size}")
        result.data.forEach { embedding ->
            println("  - index=${embedding.index}, 维度=${embedding.embedding.size}, 前3维=${embedding.embedding.take(3)}")
        }
        println("usage: promptTokens=${result.usage?.promptTokens}, totalTokens=${result.usage?.totalTokens}")
    }
}
