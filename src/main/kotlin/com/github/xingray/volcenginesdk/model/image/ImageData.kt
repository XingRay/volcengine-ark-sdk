package com.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 单张生成图片的数据。
 *
 * 根据请求中的 response_format 参数，url 和 b64Json 二选一返回。
 *
 * @property url 图片的 URL 地址
 * @property b64Json 图片的 Base64 编码数据
 * @property revisedPrompt 经过优化后的提示词
 * @property index 在生成列表中的索引
 */
@Serializable
data class ImageData(
    @SerialName("url")
    val url: String? = null,

    @SerialName("b64_json")
    val b64Json: String? = null,

    @SerialName("revised_prompt")
    val revisedPrompt: String? = null,

    @SerialName("index")
    val index: Int? = null,
)
