package com.github.xingray.volcenginesdk.model.bot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 豆包助手引用来源。
 *
 * @property url 引用链接
 * @property title 引用标题
 * @property content 引用内容摘要
 */
@Serializable
data class BotReference(
    @SerialName("url")
    val url: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("content")
    val content: String? = null,
)
