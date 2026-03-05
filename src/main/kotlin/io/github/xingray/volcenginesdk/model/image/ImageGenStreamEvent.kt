package io.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 图片生成流式事件。
 *
 * @property type 事件类型
 * @property data 图片数据
 * @property created 创建时间戳（秒）
 */
@Serializable
data class ImageGenStreamEvent(
    @SerialName("type")
    val type: String? = null,

    @SerialName("data")
    val data: ImageData? = null,

    @SerialName("created")
    val created: Long = 0,
)
