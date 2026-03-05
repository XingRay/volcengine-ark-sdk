package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Responses API SSE 流式事件。
 *
 * 包含所有可能的事件字段，根据事件类型不同，部分字段可能为空。
 *
 * @property type 事件类型
 * @property sequenceNumber 事件序列号
 * @property response 完整的响应对象
 * @property outputIndex 输出项索引
 * @property item 输出项
 * @property itemId 输出项 ID
 * @property contentIndex 内容项索引
 * @property summaryIndex 摘要索引
 * @property part 部分内容
 * @property delta 增量文本
 * @property text 完整文本
 * @property arguments 工具调用参数增量
 * @property name 工具名称
 * @property callId 工具调用 ID
 * @property annotation 注释信息
 * @property error 错误信息
 */
@Serializable
data class ResponsesStreamEvent(
    @SerialName("type")
    val type: String? = null,

    @SerialName("sequence_number")
    val sequenceNumber: Long? = null,

    @SerialName("response")
    val response: ResponseObject? = null,

    @SerialName("output_index")
    val outputIndex: Int? = null,

    @SerialName("item")
    val item: OutputItem? = null,

    @SerialName("item_id")
    val itemId: String? = null,

    @SerialName("content_index")
    val contentIndex: Int? = null,

    @SerialName("summary_index")
    val summaryIndex: Int? = null,

    @SerialName("part")
    val part: JsonElement? = null,

    @SerialName("delta")
    val delta: String? = null,

    @SerialName("text")
    val text: String? = null,

    @SerialName("arguments")
    val arguments: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("call_id")
    val callId: String? = null,

    @SerialName("annotation")
    val annotation: Annotation? = null,

    @SerialName("error")
    val error: ResponseError? = null,
)
