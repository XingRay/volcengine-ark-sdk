package com.github.xingray.volcenginesdk.model.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 列出文件的响应结果。
 *
 * @property data 文件列表
 * @property objectType 对象类型
 */
@Serializable
data class ListFilesResponse(
    @SerialName("data")
    val data: List<FileMeta> = emptyList(),

    @SerialName("object")
    val objectType: String? = null,
)
