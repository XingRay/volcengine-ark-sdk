package io.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 注释信息。
 *
 * 用于标记文本中的引用来源、链接等附加信息。
 *
 * @property type 注释类型
 * @property startIndex 注释在文本中的起始位置
 * @property endIndex 注释在文本中的结束位置
 * @property title 引用标题
 * @property url 引用链接
 */
@Serializable
data class Annotation(
    @SerialName("type")
    val type: String? = null,

    @SerialName("start_index")
    val startIndex: Int? = null,

    @SerialName("end_index")
    val endIndex: Int? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("url")
    val url: String? = null,
)
