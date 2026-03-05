package io.github.xingray.volcenginesdk.model.context

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 创建上下文的响应结果。
 *
 * @property id 上下文唯一标识
 * @property objectType 对象类型
 * @property model 使用的模型标识
 * @property mode 上下文模式
 * @property createdAt 创建时间戳
 * @property ttl 上下文存活时间（秒）
 * @property truncationStrategy 截断策略
 */
@Serializable
data class CreateContextResult(
    @SerialName("id")
    val id: String? = null,

    @SerialName("object")
    val objectType: String? = null,

    @SerialName("model")
    val model: String? = null,

    @SerialName("mode")
    val mode: String? = null,

    @SerialName("created_at")
    val createdAt: Long = 0,

    @SerialName("ttl")
    val ttl: Int? = null,

    @SerialName("truncation_strategy")
    val truncationStrategy: TruncationStrategy? = null,
)
