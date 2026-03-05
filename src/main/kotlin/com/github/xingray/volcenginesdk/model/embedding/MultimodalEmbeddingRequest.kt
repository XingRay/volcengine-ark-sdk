package com.github.xingray.volcenginesdk.model.embedding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 多模态向量化请求参数。
 *
 * @property model 模型 ID，如 "doubao-embedding-vision"
 * @property input 多模态输入列表
 * @property user 终端用户唯一标识
 */
@Serializable
data class MultimodalEmbeddingRequest(
    @SerialName("model")
    val model: String,

    @SerialName("input")
    val input: List<MultimodalEmbeddingInput>,

    @SerialName("user")
    val user: String? = null,
)
