package io.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 文本生成的单个选择结果。
 *
 * 每个 choice 包含一个完整的助手回复消息或流式增量消息。
 *
 * @property index 在返回列表中的索引
 * @property message 完整消息（非流式时返回）
 * @property delta 增量消息（流式时返回）
 * @property finishReason 停止生成的原因：stop/length/tool_calls/content_filter
 * @property moderationHitType 内容审核命中类型
 * @property logprobs token 对数概率信息
 */
@Serializable
data class ChatCompletionChoice(
    @SerialName("index")
    val index: Int? = null,

    @SerialName("message")
    val message: ChatMessage? = null,

    @SerialName("delta")
    val delta: ChatMessage? = null,

    @SerialName("finish_reason")
    val finishReason: String? = null,

    @SerialName("moderation_hit_type")
    val moderationHitType: String? = null,

    @SerialName("logprobs")
    val logprobs: ChatCompletionChoiceLogprob? = null,
)
