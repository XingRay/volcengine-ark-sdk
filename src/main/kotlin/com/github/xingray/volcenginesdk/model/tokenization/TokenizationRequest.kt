package com.github.xingray.volcenginesdk.model.tokenization

import io.github.xingray.volcenginesdk.model.chat.ChatMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Tokenization 请求参数。
 *
 * 用于计算文本或消息的 token 数量。text 和 messages 二选一。
 *
 * @property model 模型 ID，如 "doubao-seed-1.6-250615"
 * @property text 待计算的文本列表
 * @property messages 待计算的消息列表
 */
@Serializable
data class TokenizationRequest(
    @SerialName("model")
    val model: String,

    @SerialName("text")
    val text: List<String>? = null,

    @SerialName("messages")
    val messages: List<ChatMessage>? = null,
)
