package com.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * 文本生成（Chat Completion）请求参数。
 *
 * 用于向模型发送对话请求，支持多轮对话、函数调用、深度思考、结构化输出等功能。
 *
 * @property model 模型 ID，如 "doubao-seed-1.6-250615"
 * @property messages 对话消息列表
 * @property temperature 采样温度，取值范围 0~2，值越高输出越随机
 * @property topP 核采样参数，与 temperature 二选一调整
 * @property stream 是否启用流式输出
 * @property streamOptions 流式输出选项
 * @property serviceTier 服务层级，可选 "auto" 或 "default"
 * @property stop 停止序列列表，最多 4 个
 * @property maxTokens 生成的最大 token 数
 * @property maxCompletionTokens 生成的最大 token 数（含推理 token）
 * @property presencePenalty 存在惩罚，取值范围 -2.0~2.0
 * @property frequencyPenalty 频率惩罚，取值范围 -2.0~2.0
 * @property logitBias token 偏差映射
 * @property user 终端用户唯一标识
 * @property tools 可用的工具列表
 * @property toolChoice 工具选择策略
 * @property logprobs 是否返回 token 的对数概率
 * @property topLogprobs 返回概率最高的前 k 个 token，取值范围 0~20
 * @property repetitionPenalty 重复惩罚
 * @property n 每条输入消息生成的回复数量
 * @property parallelToolCalls 是否启用并行函数调用
 * @property responseFormat 响应格式配置
 * @property thinking 深度思考配置
 * @property reasoningEffort 推理力度
 */
@Serializable
data class ChatCompletionRequest(
    @SerialName("model")
    val model: String,

    @SerialName("messages")
    val messages: List<ChatMessage>,

    @SerialName("temperature")
    val temperature: Double? = null,

    @SerialName("top_p")
    val topP: Double? = null,

    @SerialName("stream")
    val stream: Boolean? = null,

    @SerialName("stream_options")
    val streamOptions: StreamOptions? = null,

    @SerialName("service_tier")
    val serviceTier: String? = null,

    @SerialName("stop")
    val stop: List<String>? = null,

    @SerialName("max_tokens")
    val maxTokens: Int? = null,

    @SerialName("max_completion_tokens")
    val maxCompletionTokens: Int? = null,

    @SerialName("presence_penalty")
    val presencePenalty: Double? = null,

    @SerialName("frequency_penalty")
    val frequencyPenalty: Double? = null,

    @SerialName("logit_bias")
    val logitBias: Map<String, Int>? = null,

    @SerialName("user")
    val user: String? = null,

    @SerialName("tools")
    val tools: List<ChatTool>? = null,

    @SerialName("tool_choice")
    val toolChoice: JsonElement? = null,

    @SerialName("logprobs")
    val logprobs: Boolean? = null,

    @SerialName("top_logprobs")
    val topLogprobs: Int? = null,

    @SerialName("repetition_penalty")
    val repetitionPenalty: Double? = null,

    @SerialName("n")
    val n: Int? = null,

    @SerialName("parallel_tool_calls")
    val parallelToolCalls: Boolean? = null,

    @SerialName("response_format")
    val responseFormat: ResponseFormat? = null,

    @SerialName("thinking")
    val thinking: Thinking? = null,

    @SerialName("reasoning_effort")
    val reasoningEffort: String? = null,
)
