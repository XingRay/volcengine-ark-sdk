package com.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.ArkClient
import io.github.xingray.volcenginesdk.ArkConstants
import io.github.xingray.volcenginesdk.model.embedding.*
import io.github.xingray.volcenginesdk.util.checkSuccess
import io.ktor.client.call.*

/**
 * 文本向量化 API。
 *
 * 支持文本向量化、批量文本向量化、多模态向量化等。
 *
 * API 端点:
 * - POST /embeddings - 文本向量化
 * - POST /batch/embeddings - 批量文本向量化
 * - POST /embeddings/multimodal - 多模态向量化
 * - POST /batch/embeddings/multimodal - 批量多模态向量化
 *
 * @param client ArkClient 实例
 */
class EmbeddingApi(private val client: ArkClient) {

    /**
     * 创建文本向量化。
     *
     * 将输入文本转换为高维向量表示，可用于语义搜索、相似度计算等场景。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 文本向量化请求参数
     * @return 向量化结果，包含每条输入对应的向量
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createEmbeddings(apiKey: String, request: EmbeddingRequest): EmbeddingResult {
        val response = client.post(apiKey, ArkConstants.Endpoint.EMBEDDINGS, request)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 创建批量文本向量化。
     *
     * 一次性处理大量文本的向量化请求，适用于批量数据处理场景。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 文本向量化请求参数
     * @return 向量化结果，包含每条输入对应的向量
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createBatchEmbeddings(apiKey: String, request: EmbeddingRequest): EmbeddingResult {
        val response = client.post(apiKey, ArkConstants.Endpoint.BATCH_EMBEDDINGS, request)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 创建多模态向量化。
     *
     * 支持文本和图片的联合向量化，将不同模态的输入统一映射到同一向量空间。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 多模态向量化请求参数
     * @return 多模态向量化结果，包含每条输入对应的向量
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createMultimodalEmbeddings(apiKey: String, request: MultimodalEmbeddingRequest): MultimodalEmbeddingResult {
        val response = client.post(apiKey, ArkConstants.Endpoint.MULTIMODAL_EMBEDDINGS, request)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 创建批量多模态向量化。
     *
     * 一次性处理大量多模态输入（文本和图片）的向量化请求。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 多模态向量化请求参数
     * @return 多模态向量化结果，包含每条输入对应的向量
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createBatchMultimodalEmbeddings(apiKey: String, request: MultimodalEmbeddingRequest): MultimodalEmbeddingResult {
        val response = client.post(apiKey, ArkConstants.Endpoint.BATCH_MULTIMODAL_EMBEDDINGS, request)
        response.checkSuccess()
        return response.body()
    }
}
