package io.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 图片生成响应结果。
 *
 * @property created 创建时间戳（秒）
 * @property data 生成的图片数据列表
 */
@Serializable
data class ImagesResponse(
    @SerialName("created")
    val created: Long = 0,

    @SerialName("data")
    val data: List<ImageData> = emptyList(),
)
