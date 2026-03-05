package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Responses API 深度思考配置。
 *
 * @property type 思考模式类型
 * @property budgetTokens 思考预算 token 数
 */
@Serializable
data class ResponsesThinking(
    @SerialName("type")
    val type: String,

    @SerialName("budget_tokens")
    val budgetTokens: Long? = null,
)
