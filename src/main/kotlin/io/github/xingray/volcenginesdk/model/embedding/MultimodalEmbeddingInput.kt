package io.github.xingray.volcenginesdk.model.embedding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 多模态向量化输入项。
 *
 * @property type 输入类型，如 "text"、"image_url"
 * @property text 文本内容（当 type 为 "text" 时使用）
 * @property imageUrl 图片 URL（当 type 为 "image_url" 时使用）
 */
@Serializable
data class MultimodalEmbeddingInput(
    @SerialName("type")
    val type: String,

    @SerialName("text")
    val text: String? = null,

    @SerialName("image_url")
    val imageUrl: String? = null,
)
