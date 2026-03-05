package io.github.xingray.volcenginesdk.model.context

import io.github.xingray.volcenginesdk.model.chat.ChatMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 创建上下文请求参数。
 *
 * @property model 模型 ID，如 "doubao-seed-1.6-250615"
 * @property mode 上下文模式，如 "session" 或 "prefix"
 * @property messages 初始消息列表
 * @property ttl 上下文存活时间（秒）
 */
@Serializable
data class CreateContextRequest(
    @SerialName("model")
    val model: String,

    @SerialName("mode")
    val mode: String,

    @SerialName("messages")
    val messages: List<ChatMessage>? = null,

    @SerialName("ttl")
    val ttl: Int? = null,
)
