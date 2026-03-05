package com.github.xingray.volcenginesdk.model.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 内容生成任务的错误信息。
 *
 * @property code 错误码
 * @property message 错误描述
 */
@Serializable
data class ContentGenerationError(
    @SerialName("code")
    val code: String? = null,

    @SerialName("message")
    val message: String? = null,
)
