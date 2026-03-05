package com.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 流式输出选项。
 *
 * 仅在 stream=true 时有效。
 *
 * @property includeUsage 是否在流式输出中包含 token 使用统计
 * @property chunkIncludeUsage 是否在每个 chunk 中包含 token 使用统计
 */
@Serializable
data class StreamOptions(
    @SerialName("include_usage")
    val includeUsage: Boolean? = null,

    @SerialName("chunk_include_usage")
    val chunkIncludeUsage: Boolean? = null,
)
