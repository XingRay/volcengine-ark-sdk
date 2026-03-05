package io.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 函数调用信息。
 *
 * 当模型决定调用某个函数时，会返回函数名和参数。
 *
 * @property name 要调用的函数名称
 * @property arguments 函数参数，JSON 格式的字符串
 */
@Serializable
data class ChatFunctionCall(
    @SerialName("name")
    val name: String? = null,

    @SerialName("arguments")
    val arguments: String? = null,
)
