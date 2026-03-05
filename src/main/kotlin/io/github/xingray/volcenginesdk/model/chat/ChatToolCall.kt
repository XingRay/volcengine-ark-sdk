package io.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 工具调用信息。
 *
 * 模型请求调用外部工具时返回的调用详情。
 *
 * @property id 工具调用的唯一标识
 * @property type 工具类型，通常为 "function"
 * @property function 具体的函数调用信息
 */
@Serializable
data class ChatToolCall(
    @SerialName("id")
    val id: String? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("function")
    val function: ChatFunctionCall? = null,
)
