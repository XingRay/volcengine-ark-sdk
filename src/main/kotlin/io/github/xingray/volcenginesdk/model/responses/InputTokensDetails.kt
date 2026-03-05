package io.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 输入 token 详情。
 *
 * @property cachedTokens 缓存命中的 token 数
 */
@Serializable
data class InputTokensDetails(
    @SerialName("cached_tokens")
    val cachedTokens: Int = 0,
)
