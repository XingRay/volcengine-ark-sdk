package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Responses API 工具定义。
 *
 * 支持多种工具类型：function（函数调用）、web_search（联网搜索）、
 * mcp（MCP 工具）、image_process（图像处理）、doubao_app（豆包应用）等。
 *
 * @property type 工具类型
 * @property name 工具名称（函数调用时使用）
 * @property description 工具描述
 * @property parameters 工具参数的 JSON Schema
 * @property strict 是否启用严格模式
 * @property serverLabel MCP 服务标签
 * @property serverUrl MCP 服务地址
 * @property requireApproval MCP 审批配置
 * @property allowedTools MCP 允许的工具列表
 * @property maxKeyword 联网搜索最大关键词数
 * @property searchRecencyFilter 联网搜索时间过滤
 * @property userLocation 用户位置信息
 * @property actions 图像处理动作列表
 */
@Serializable
data class ResponsesTool(
    @SerialName("type")
    val type: String,

    @SerialName("name")
    val name: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("parameters")
    val parameters: JsonElement? = null,

    @SerialName("strict")
    val strict: Boolean? = null,

    @SerialName("server_label")
    val serverLabel: String? = null,

    @SerialName("server_url")
    val serverUrl: String? = null,

    @SerialName("require_approval")
    val requireApproval: JsonElement? = null,

    @SerialName("allowed_tools")
    val allowedTools: JsonElement? = null,

    @SerialName("max_keyword")
    val maxKeyword: Int? = null,

    @SerialName("search_recency_filter")
    val searchRecencyFilter: String? = null,

    @SerialName("user_location")
    val userLocation: UserLocation? = null,

    @SerialName("actions")
    val actions: List<JsonElement>? = null,
)
