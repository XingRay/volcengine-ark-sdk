package com.github.xingray.volcenginesdk.model.context

import io.github.xingray.volcenginesdk.model.chat.ChatMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 基于上下文的文本生成请求参数。
 *
 * @property model 模型 ID，如 "doubao-seed-1.6-250615"
 * @property contextId 上下文唯一标识
 * @property messages 当前轮对话消息列表
 * @property temperature 采样温度
 * @property topP 核采样参数
 * @property stream 是否启用流式输出
 * @property maxTokens 生成的最大 token 数
 */
@Serializable
data class ContextChatCompletionRequest(
    @SerialName("model")
    val model: String,

    @SerialName("context_id")
    val contextId: String,

    @SerialName("messages")
    val messages: List<ChatMessage>,

    @SerialName("temperature")
    val temperature: Double? = null,

    @SerialName("top_p")
    val topP: Double? = null,

    @SerialName("stream")
    val stream: Boolean? = null,

    @SerialName("max_tokens")
    val maxTokens: Int? = null,
)
