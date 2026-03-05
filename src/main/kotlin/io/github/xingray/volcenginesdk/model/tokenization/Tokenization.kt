package io.github.xingray.volcenginesdk.model.tokenization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 单条输入的 token 统计。
 *
 * @property objectType 对象类型
 * @property totalTokens 总 token 数量
 * @property index 在输入列表中的索引
 */
@Serializable
data class Tokenization(
    @SerialName("object")
    val objectType: String? = null,

    @SerialName("total_tokens")
    val totalTokens: Int = 0,

    @SerialName("index")
    val index: Int = 0,
)
