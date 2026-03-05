package io.github.xingray.volcenginesdk.model.chat

import io.github.xingray.volcenginesdk.model.Usage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 文本生成（Chat Completion）响应结果。
 *
 * 非流式调用时返回的完整响应对象。
 *
 * @property id 本次请求的唯一标识
 * @property objectType 对象类型，固定为 "chat.completion"
 * @property created 创建时间戳（秒）
 * @property model 实际使用的模型标识
 * @property serviceTier 实际使用的服务层级
 * @property choices 生成结果列表
 * @property usage token 使用统计
 */
@Serializable
data class ChatCompletionResult(
    @SerialName("id")
    val id: String? = null,

    @SerialName("object")
    val objectType: String? = null,

    @SerialName("created")
    val created: Long = 0,

    @SerialName("model")
    val model: String? = null,

    @SerialName("service_tier")
    val serviceTier: String? = null,

    @SerialName("choices")
    val choices: List<ChatCompletionChoice> = emptyList(),

    @SerialName("usage")
    val usage: Usage? = null,
)
