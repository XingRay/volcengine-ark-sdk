package io.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 聊天消息角色枚举。
 *
 * 定义了对话中各参与方的角色类型。
 */
@Serializable
enum class ChatMessageRole {
    /** 系统消息，用于设定模型的行为和背景 */
    @SerialName("system")
    SYSTEM,

    /** 用户消息，用户发送的输入内容 */
    @SerialName("user")
    USER,

    /** 助手消息，模型生成的回复内容 */
    @SerialName("assistant")
    ASSISTANT,

    /** 工具消息，工具/函数调用的结果 */
    @SerialName("tool")
    TOOL,

    /** 函数消息（已废弃），函数调用的结果 */
    @SerialName("function")
    FUNCTION,
}
