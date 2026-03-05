package io.github.xingray.volcenginesdk.model.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 内容生成任务的输入内容项。
 *
 * @property type 内容类型，如 "text"、"image_url"、"video_url"
 * @property text 文本描述
 * @property imageUrl 参考图片 URL
 * @property videoUrl 参考视频 URL
 */
@Serializable
data class ContentItem(
    @SerialName("type")
    val type: String,

    @SerialName("text")
    val text: String? = null,

    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("video_url")
    val videoUrl: String? = null,
)
