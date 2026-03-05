package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 错误信息。
 *
 * @property code 错误码
 * @property message 错误描述
 */
@Serializable
data class ResponseError(
    @SerialName("code")
    val code: String? = null,

    @SerialName("message")
    val message: String? = null,
)
