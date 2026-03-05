package io.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Responses API 缓存配置。
 *
 * @property type 缓存类型
 */
@Serializable
data class ResponsesCaching(
    @SerialName("type")
    val type: String,
)
