package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 输出 token 详情。
 *
 * @property reasoningTokens 推理 token 数
 */
@Serializable
data class OutputTokensDetails(
    @SerialName("reasoning_tokens")
    val reasoningTokens: Int = 0,
)
