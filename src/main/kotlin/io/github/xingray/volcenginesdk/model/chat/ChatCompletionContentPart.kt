package io.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 多模态内容片段。
 *
 * 用于在消息中组合文本、图片、视频等不同类型的内容。
 *
 * @property type 内容类型，如 "text"、"image_url"、"video_url"
 * @property text 文本内容（当 type 为 "text" 时使用）
 * @property imageUrl 图片 URL 信息（当 type 为 "image_url" 时使用）
 */
@Serializable
data class ChatCompletionContentPart(
    @SerialName("type")
    val type: String,

    @SerialName("text")
    val text: String? = null,

    @SerialName("image_url")
    val imageUrl: ImageUrl? = null,
)

/**
 * 图片 URL 信息。
 *
 * @property url 图片的 URL 地址或 Base64 编码数据
 * @property detail 图片解析精度，可选 "auto"、"low"、"high"
 */
@Serializable
data class ImageUrl(
    @SerialName("url")
    val url: String,

    @SerialName("detail")
    val detail: String? = null,
)
