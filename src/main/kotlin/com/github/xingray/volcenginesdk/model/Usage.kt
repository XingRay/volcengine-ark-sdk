package com.github.xingray.volcenginesdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Token 使用统计信息。
 *
 * 记录一次 API 调用中输入和输出 token 的消耗量。
 *
 * @property promptTokens 输入（提示词）消耗的 token 数量
 * @property completionTokens 输出（生成内容）消耗的 token 数量
 * @property totalTokens 总共消耗的 token 数量
 * @property promptTokensDetails 输入 token 详情
 * @property completionTokensDetails 输出 token 详情
 */
@Serializable
data class Usage(
    @SerialName("prompt_tokens")
    val promptTokens: Int = 0,

    @SerialName("completion_tokens")
    val completionTokens: Int = 0,

    @SerialName("total_tokens")
    val totalTokens: Int = 0,

    @SerialName("prompt_tokens_details")
    val promptTokensDetails: PromptTokensDetails? = null,

    @SerialName("completion_tokens_details")
    val completionTokensDetails: CompletionTokensDetails? = null,
)
