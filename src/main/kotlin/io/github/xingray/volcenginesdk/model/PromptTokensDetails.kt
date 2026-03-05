package io.github.xingray.volcenginesdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 输入 token 详情。
 *
 * @property cachedTokens 命中缓存的 token 数量
 */
@Serializable
data class PromptTokensDetails(
    @SerialName("cached_tokens")
    val cachedTokens: Int = 0,
)
