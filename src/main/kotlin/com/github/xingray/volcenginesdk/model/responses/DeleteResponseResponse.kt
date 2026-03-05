package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 删除 Response 的响应结果。
 *
 * @property id 被删除的 Response ID
 * @property objectType 对象类型
 * @property deleted 是否删除成功
 */
@Serializable
data class DeleteResponseResponse(
    @SerialName("id")
    val id: String? = null,

    @SerialName("object")
    val objectType: String? = null,

    @SerialName("deleted")
    val deleted: Boolean = false,
)
