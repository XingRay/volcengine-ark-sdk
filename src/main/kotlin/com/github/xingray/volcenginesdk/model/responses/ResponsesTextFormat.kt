package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Responses API 文本格式定义。
 *
 * @property type 格式类型，如 "text"、"json_object"、"json_schema"
 * @property name Schema 名称
 * @property description Schema 描述
 * @property schema JSON Schema 定义
 * @property strict 是否启用严格模式
 */
@Serializable
data class ResponsesTextFormat(
    @SerialName("type")
    val type: String,

    @SerialName("name")
    val name: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("schema")
    val schema: JsonElement? = null,

    @SerialName("strict")
    val strict: Boolean? = null,
)
