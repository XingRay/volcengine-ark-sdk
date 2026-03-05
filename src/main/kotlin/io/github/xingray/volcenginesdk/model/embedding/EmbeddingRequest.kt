package io.github.xingray.volcenginesdk.model.embedding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 文本向量化请求参数。
 *
 * @property model 模型 ID，如 "doubao-embedding"
 * @property input 待向量化的文本列表
 * @property encodingFormat 向量编码格式
 * @property user 终端用户唯一标识
 */
@Serializable
data class EmbeddingRequest(
    @SerialName("model")
    val model: String,

    @SerialName("input")
    val input: List<String>,

    @SerialName("encoding_format")
    val encodingFormat: String? = null,

    @SerialName("user")
    val user: String? = null,
)
