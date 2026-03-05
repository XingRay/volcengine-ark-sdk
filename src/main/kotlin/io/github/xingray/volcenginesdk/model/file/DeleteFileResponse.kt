package io.github.xingray.volcenginesdk.model.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 删除文件的响应结果。
 *
 * @property id 被删除的文件标识
 * @property objectType 对象类型
 * @property deleted 是否删除成功
 */
@Serializable
data class DeleteFileResponse(
    @SerialName("id")
    val id: String? = null,

    @SerialName("object")
    val objectType: String? = null,

    @SerialName("deleted")
    val deleted: Boolean = false,
)
