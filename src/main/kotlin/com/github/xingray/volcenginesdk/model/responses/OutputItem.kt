package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Responses API 输出项。
 *
 * 表示一个消息、工具调用、推理摘要等。
 *
 * @property id 输出项唯一标识
 * @property type 输出项类型
 * @property status 输出项状态
 * @property role 角色
 * @property content 内容项列表
 * @property summary 推理摘要部分列表
 * @property name 工具名称
 * @property callId 工具调用 ID
 * @property arguments 工具调用参数
 * @property output 工具输出结果
 * @property action 动作定义
 * @property results 结果数据
 */
@Serializable
data class OutputItem(
    @SerialName("id")
    val id: String? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("role")
    val role: String? = null,

    @SerialName("content")
    val content: List<OutputContentItem>? = null,

    @SerialName("summary")
    val summary: List<ReasoningSummaryPart>? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("call_id")
    val callId: String? = null,

    @SerialName("arguments")
    val arguments: String? = null,

    @SerialName("output")
    val output: String? = null,

    @SerialName("action")
    val action: JsonElement? = null,

    @SerialName("results")
    val results: JsonElement? = null,
)
