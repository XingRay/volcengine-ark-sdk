package com.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * 工具定义。
 *
 * 描述一个可供模型调用的工具。
 *
 * @property type 工具类型，目前仅支持 "function"
 * @property function 函数定义详情
 */
@Serializable
data class ChatTool(
    @SerialName("type")
    val type: String = "function",

    @SerialName("function")
    val function: ChatFunction,
)
