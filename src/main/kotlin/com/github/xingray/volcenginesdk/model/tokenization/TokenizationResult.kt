package com.github.xingray.volcenginesdk.model.tokenization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Tokenization 响应结果。
 *
 * @property objectType 对象类型
 * @property data 各输入的 token 统计列表
 * @property model 使用的模型标识
 */
@Serializable
data class TokenizationResult(
    @SerialName("object")
    val objectType: String? = null,

    @SerialName("data")
    val data: List<Tokenization> = emptyList(),

    @SerialName("model")
    val model: String? = null,
)
