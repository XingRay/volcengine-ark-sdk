package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 推理摘要部分。
 *
 * @property type 摘要部分类型
 * @property text 摘要文本内容
 */
@Serializable
data class ReasoningSummaryPart(
    @SerialName("type")
    val type: String? = null,

    @SerialName("text")
    val text: String? = null,
)
