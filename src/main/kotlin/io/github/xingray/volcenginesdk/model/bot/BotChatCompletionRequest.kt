package io.github.xingray.volcenginesdk.model.bot

import io.github.xingray.volcenginesdk.model.chat.ChatMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * 豆包助手对话请求参数。
 *
 * @property model Bot 应用的 Model ID
 * @property messages 对话消息列表
 * @property stream 是否启用流式输出
 * @property maxTokens 生成的最大 token 数
 * @property temperature 采样温度
 * @property topP 核采样参数
 * @property metadata 自定义元数据
 */
@Serializable
data class BotChatCompletionRequest(
    @SerialName("model")
    val model: String,

    @SerialName("messages")
    val messages: List<ChatMessage>,

    @SerialName("stream")
    val stream: Boolean? = null,

    @SerialName("max_tokens")
    val maxTokens: Int? = null,

    @SerialName("temperature")
    val temperature: Double? = null,

    @SerialName("top_p")
    val topP: Double? = null,

    @SerialName("metadata")
    val metadata: JsonElement? = null,
)
