package com.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * 响应格式配置。
 *
 * 控制模型输出的格式，支持纯文本、JSON 对象、JSON Schema 等。
 *
 * @property type 格式类型，可选 "text"、"json_object"、"json_schema"
 * @property jsonSchema JSON Schema 参数（当 type 为 "json_schema" 时使用）
 */
@Serializable
data class ResponseFormat(
    @SerialName("type")
    val type: String,

    @SerialName("json_schema")
    val jsonSchema: JsonSchemaParam? = null,
)

/**
 * JSON Schema 参数。
 *
 * 定义结构化输出的 JSON Schema 约束。
 *
 * @property name Schema 名称
 * @property description Schema 描述
 * @property schema JSON Schema 定义
 * @property strict 是否启用严格模式
 */
@Serializable
data class JsonSchemaParam(
    @SerialName("name")
    val name: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("schema")
    val schema: JsonElement? = null,

    @SerialName("strict")
    val strict: Boolean? = null,
)
