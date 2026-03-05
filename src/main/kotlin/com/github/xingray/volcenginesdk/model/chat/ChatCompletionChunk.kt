package com.github.xingray.volcenginesdk.model.chat

import io.github.xingray.volcenginesdk.model.Usage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 流式文本生成的数据块。
 *
 * 通过 SSE 协议逐块返回，每个 chunk 包含一个增量的生成内容。
 *
 * @property id 本次请求的唯一标识
 * @property objectType 对象类型，固定为 "chat.completion.chunk"
 * @property created 创建时间戳（秒）
 * @property model 实际使用的模型标识
 * @property serviceTier 实际使用的服务层级
 * @property choices 增量结果列表
 * @property usage token 使用统计（仅在最后一个 chunk 中包含）
 */
@Serializable
data class ChatCompletionChunk(
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
