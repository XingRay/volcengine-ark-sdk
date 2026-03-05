package io.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.ArkClient
import io.github.xingray.volcenginesdk.ArkConstants
import io.github.xingray.volcenginesdk.model.tokenization.*
import io.github.xingray.volcenginesdk.util.checkSuccess
import io.ktor.client.call.*

/**
 * Tokenization API。
 *
 * 计算文本或消息的 token 数量，用于预估 API 调用的 token 消耗，
 * 帮助优化请求参数和控制成本。
 *
 * API 端点: POST /tokenization
 *
 * @param client ArkClient 实例
 */
class TokenizationApi(private val client: ArkClient) {

    /**
     * 计算 token 数量。
     *
     * 根据指定的模型对输入文本或消息列表进行 tokenization，
     * 返回 token 总数及各部分的 token 明细。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request Tokenization 请求参数，包含模型 ID 和待分析的文本或消息
     * @return Tokenization 结果，包含 token 总数和明细信息
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createTokenization(apiKey: String, request: TokenizationRequest): TokenizationResult {
        val response = client.post(apiKey, ArkConstants.Endpoint.TOKENIZATION, request)
        response.checkSuccess()
        return response.body()
    }
}
