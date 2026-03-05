package io.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * 函数定义。
 *
 * 描述一个可被模型调用的函数的元信息。
 *
 * @property name 函数名称
 * @property description 函数用途描述，帮助模型判断何时调用
 * @property parameters 函数参数的 JSON Schema 定义
 */
@Serializable
data class ChatFunction(
    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String? = null,

    @SerialName("parameters")
    val parameters: JsonObject? = null,
)
