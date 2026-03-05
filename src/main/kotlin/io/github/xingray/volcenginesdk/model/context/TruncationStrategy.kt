package io.github.xingray.volcenginesdk.model.context

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 上下文截断策略。
 *
 * @property type 截断类型
 * @property maxTokens 最大 token 数
 */
@Serializable
data class TruncationStrategy(
    @SerialName("type")
    val type: String? = null,

    @SerialName("max_tokens")
    val maxTokens: Int? = null,
)
