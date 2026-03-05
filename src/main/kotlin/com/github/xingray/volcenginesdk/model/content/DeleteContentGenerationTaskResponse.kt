package com.github.xingray.volcenginesdk.model.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 删除内容生成任务的响应结果。
 *
 * @property id 被删除的任务标识
 * @property deleted 是否删除成功
 */
@Serializable
data class DeleteContentGenerationTaskResponse(
    @SerialName("id")
    val id: String? = null,

    @SerialName("deleted")
    val deleted: Boolean = false,
)
