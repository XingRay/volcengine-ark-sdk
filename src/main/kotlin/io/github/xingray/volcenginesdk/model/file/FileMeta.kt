package io.github.xingray.volcenginesdk.model.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 文件元信息。
 *
 * @property id 文件唯一标识
 * @property objectType 对象类型
 * @property bytes 文件大小（字节）
 * @property createdAt 创建时间戳
 * @property filename 文件名
 * @property purpose 文件用途
 */
@Serializable
data class FileMeta(
    @SerialName("id")
    val id: String? = null,

    @SerialName("object")
    val objectType: String? = null,

    @SerialName("bytes")
    val bytes: Long = 0,

    @SerialName("created_at")
    val createdAt: Long = 0,

    @SerialName("filename")
    val filename: String? = null,

    @SerialName("purpose")
    val purpose: String? = null,
)
