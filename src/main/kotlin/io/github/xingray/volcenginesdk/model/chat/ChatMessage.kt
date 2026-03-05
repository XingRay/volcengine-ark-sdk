package io.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * 聊天消息。
 *
 * 表示对话中的一条消息，包含角色、内容、工具调用等信息。
 * content 字段使用 JsonElement 以支持纯文本（String）和多模态内容（Array）两种格式。
 *
 * @property role 消息角色
 * @property content 消息内容，可以是字符串或多模态内容数组的 JSON 表示
 * @property reasoningContent 深度思考时模型的推理过程内容
 * @property name 消息发送者的名称标识
 * @property functionCall 函数调用信息（已废弃，建议使用 toolCalls）
 * @property toolCalls 工具调用列表
 * @property toolCallId 工具调用的唯一标识，用于关联工具结果和调用请求
 */
@Serializable
data class ChatMessage(
    @SerialName("role")
    val role: ChatMessageRole,

    @SerialName("content")
    val content: JsonElement? = null,

    @SerialName("reasoning_content")
    val reasoningContent: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("function_call")
    val functionCall: ChatFunctionCall? = null,

    @SerialName("tool_calls")
    val toolCalls: List<ChatToolCall>? = null,

    @SerialName("tool_call_id")
    val toolCallId: String? = null,
)
