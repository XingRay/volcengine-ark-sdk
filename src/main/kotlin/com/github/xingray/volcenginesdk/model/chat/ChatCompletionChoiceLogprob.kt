package com.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 对数概率信息。
 *
 * @property content token 对数概率列表
 */
@Serializable
data class ChatCompletionChoiceLogprob(
    @SerialName("content")
    val content: List<TokenLogprob>? = null,
)

/**
 * 单个 token 的对数概率。
 *
 * @property token token 文本
 * @property logprob 对数概率值
 * @property bytes token 的字节表示
 * @property topLogprobs 概率最高的 k 个备选 token
 */
@Serializable
data class TokenLogprob(
    @SerialName("token")
    val token: String? = null,

    @SerialName("logprob")
    val logprob: Double? = null,

    @SerialName("bytes")
    val bytes: List<Int>? = null,

    @SerialName("top_logprobs")
    val topLogprobs: List<TopLogprob>? = null,
)

/**
 * 备选 token 的对数概率。
 *
 * @property token token 文本
 * @property logprob 对数概率值
 * @property bytes token 的字节表示
 */
@Serializable
data class TopLogprob(
    @SerialName("token")
    val token: String? = null,

    @SerialName("logprob")
    val logprob: Double? = null,

    @SerialName("bytes")
    val bytes: List<Int>? = null,
)
