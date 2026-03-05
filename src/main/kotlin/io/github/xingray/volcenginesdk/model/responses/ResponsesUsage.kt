package io.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Responses API 用量统计。
 *
 * @property inputTokens 输入 token 数
 * @property outputTokens 输出 token 数
 * @property totalTokens 总 token 数
 * @property inputTokensDetails 输入 token 详情
 * @property outputTokensDetails 输出 token 详情
 */
@Serializable
data class ResponsesUsage(
    @SerialName("input_tokens")
    val inputTokens: Int = 0,

    @SerialName("output_tokens")
    val outputTokens: Int = 0,

    @SerialName("total_tokens")
    val totalTokens: Int = 0,

    @SerialName("input_tokens_details")
    val inputTokensDetails: InputTokensDetails? = null,

    @SerialName("output_tokens_details")
    val outputTokensDetails: OutputTokensDetails? = null,
)
