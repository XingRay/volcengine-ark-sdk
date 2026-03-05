package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Responses API 响应对象。
 *
 * @property createdAt 创建时间戳
 * @property error 错误信息
 * @property id Response 唯一标识
 * @property incompleteDetails 不完整原因
 * @property maxOutputTokens 最大输出 token 数
 * @property model 使用的模型标识
 * @property objectType 对象类型
 * @property output 输出项列表
 * @property previousResponseId 上一轮 Response ID
 * @property thinking 深度思考配置
 * @property reasoning 推理配置
 * @property serviceTier 服务层级
 * @property status 状态：completed/failed/incomplete/in_progress
 * @property temperature 采样温度
 * @property tools 工具列表
 * @property topP 核采样参数
 * @property usage 用量统计
 * @property caching 缓存配置
 * @property text 文本格式配置
 * @property instructions 系统指令
 * @property store 是否已存储
 * @property expireAt 过期时间戳
 * @property toolChoice 工具选择策略
 * @property parallelToolCalls 是否并行工具调用
 * @property maxToolCalls 最大工具调用次数
 */
@Serializable
data class ResponseObject(
    @SerialName("created_at")
    val createdAt: Long? = null,

    @SerialName("error")
    val error: ResponseError? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("incomplete_details")
    val incompleteDetails: IncompleteDetails? = null,

    @SerialName("max_output_tokens")
    val maxOutputTokens: Long? = null,

    @SerialName("model")
    val model: String? = null,

    @SerialName("object")
    val objectType: String? = null,

    @SerialName("output")
    val output: List<OutputItem>? = null,

    @SerialName("previous_response_id")
    val previousResponseId: String? = null,

    @SerialName("thinking")
    val thinking: ResponsesThinking? = null,

    @SerialName("reasoning")
    val reasoning: ResponsesReasoning? = null,

    @SerialName("service_tier")
    val serviceTier: String? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("temperature")
    val temperature: Double? = null,

    @SerialName("tools")
    val tools: List<ResponsesTool>? = null,

    @SerialName("top_p")
    val topP: Double? = null,

    @SerialName("usage")
    val usage: ResponsesUsage? = null,

    @SerialName("caching")
    val caching: ResponsesCaching? = null,

    @SerialName("text")
    val text: ResponsesText? = null,

    @SerialName("instructions")
    val instructions: String? = null,

    @SerialName("store")
    val store: Boolean? = null,

    @SerialName("expire_at")
    val expireAt: Long? = null,

    @SerialName("tool_choice")
    val toolChoice: JsonElement? = null,

    @SerialName("parallel_tool_calls")
    val parallelToolCalls: Boolean? = null,

    @SerialName("max_tool_calls")
    val maxToolCalls: Long? = null,
)
