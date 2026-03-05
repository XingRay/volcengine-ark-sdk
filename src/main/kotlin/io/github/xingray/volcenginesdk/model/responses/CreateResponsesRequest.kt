package io.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Responses API 创建请求参数。
 *
 * 新一代推荐 API，支持文本生成、多模态理解、工具调用、上下文缓存等。
 *
 * @property model 模型 ID，如 "doubao-seed-1.6-250615"
 * @property input 输入内容，可以是字符串或消息列表的 JSON 表示
 * @property maxOutputTokens 最大输出 token 数
 * @property previousResponseId 上一轮 Response 的 ID（用于多轮对话）
 * @property thinking 深度思考配置
 * @property reasoning 推理配置
 * @property serviceTier 服务层级
 * @property store 是否存储 Response
 * @property stream 是否启用流式输出
 * @property temperature 采样温度
 * @property tools 可用的工具列表
 * @property topP 核采样参数
 * @property instructions 系统指令
 * @property include 额外包含的信息
 * @property caching 缓存配置
 * @property text 文本输出格式配置
 * @property expireAt 过期时间戳
 * @property toolChoice 工具选择策略
 * @property parallelToolCalls 是否启用并行工具调用
 * @property maxToolCalls 最大工具调用次数
 */
@Serializable
data class CreateResponsesRequest(
    @SerialName("model")
    val model: String,

    @SerialName("input")
    val input: JsonElement,

    @SerialName("max_output_tokens")
    val maxOutputTokens: Long? = null,

    @SerialName("previous_response_id")
    val previousResponseId: String? = null,

    @SerialName("thinking")
    val thinking: ResponsesThinking? = null,

    @SerialName("reasoning")
    val reasoning: ResponsesReasoning? = null,

    @SerialName("service_tier")
    val serviceTier: String? = null,

    @SerialName("store")
    val store: Boolean? = null,

    @SerialName("stream")
    val stream: Boolean? = null,

    @SerialName("temperature")
    val temperature: Double? = null,

    @SerialName("tools")
    val tools: List<ResponsesTool>? = null,

    @SerialName("top_p")
    val topP: Double? = null,

    @SerialName("instructions")
    val instructions: String? = null,

    @SerialName("include")
    val include: List<String>? = null,

    @SerialName("caching")
    val caching: ResponsesCaching? = null,

    @SerialName("text")
    val text: ResponsesText? = null,

    @SerialName("expire_at")
    val expireAt: Long? = null,

    @SerialName("tool_choice")
    val toolChoice: JsonElement? = null,

    @SerialName("parallel_tool_calls")
    val parallelToolCalls: Boolean? = null,

    @SerialName("max_tool_calls")
    val maxToolCalls: Long? = null,
)
