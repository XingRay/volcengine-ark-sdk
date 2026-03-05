package io.github.xingray.volcenginesdk.model.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 内容生成任务的输出内容。
 *
 * @property videoUrl 生成的视频 URL
 * @property modelUrl 生成的 3D 模型 URL
 * @property coverImageUrl 封面图 URL
 */
@Serializable
data class ContentGenerationContent(
    @SerialName("video_url")
    val videoUrl: String? = null,

    @SerialName("model_url")
    val modelUrl: String? = null,

    @SerialName("cover_image_url")
    val coverImageUrl: String? = null,
)
