package io.github.xingray.volcenginesdk.model.embedding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 单条多模态输入的向量化结果。
 *
 * @property objectType 对象类型
 * @property embedding 向量值列表
 * @property index 在输入列表中的索引
 */
@Serializable
data class MultimodalEmbedding(
    @SerialName("object")
    val objectType: String? = null,

    @SerialName("embedding")
    val embedding: List<Double> = emptyList(),

    @SerialName("index")
    val index: Int = 0,
)
