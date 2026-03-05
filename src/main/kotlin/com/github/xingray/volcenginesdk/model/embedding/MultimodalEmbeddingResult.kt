package com.github.xingray.volcenginesdk.model.embedding

import com.github.xingray.volcenginesdk.model.Usage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 多模态向量化响应结果。
 *
 * @property objectType 对象类型
 * @property data 向量化结果列表
 * @property model 使用的模型标识
 * @property usage token 使用统计
 */
@Serializable
data class MultimodalEmbeddingResult(
    @SerialName("object")
    val objectType: String? = null,

    @SerialName("data")
    val data: List<MultimodalEmbedding> = emptyList(),

    @SerialName("model")
    val model: String? = null,

    @SerialName("usage")
    val usage: Usage? = null,
)
