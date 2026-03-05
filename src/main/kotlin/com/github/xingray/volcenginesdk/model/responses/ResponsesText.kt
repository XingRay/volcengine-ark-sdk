package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Responses API 文本输出格式配置。
 *
 * @property format 文本格式定义
 */
@Serializable
data class ResponsesText(
    @SerialName("format")
    val format: ResponsesTextFormat? = null,
)
