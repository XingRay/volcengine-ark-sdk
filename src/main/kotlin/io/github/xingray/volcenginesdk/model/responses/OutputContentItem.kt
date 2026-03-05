package io.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Responses API 输出内容项。
 *
 * 表示输出消息中的一个内容块，如文本、引用等。
 *
 * @property type 内容类型，如 "output_text"、"refusal" 等
 * @property text 文本内容
 * @property annotations 注释列表
 */
@Serializable
data class OutputContentItem(
    @SerialName("type")
    val type: String? = null,

    @SerialName("text")
    val text: String? = null,

    @SerialName("annotations")
    val annotations: List<Annotation>? = null,
)
