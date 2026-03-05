package com.github.xingray.volcenginesdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 输出 token 详情。
 *
 * @property reasoningTokens 推理过程消耗的 token 数量
 */
@Serializable
data class CompletionTokensDetails(
    @SerialName("reasoning_tokens")
    val reasoningTokens: Int = 0,
)
