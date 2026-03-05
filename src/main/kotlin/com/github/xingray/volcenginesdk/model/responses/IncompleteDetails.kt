package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 不完整原因详情。
 *
 * @property reason 原因
 */
@Serializable
data class IncompleteDetails(
    @SerialName("reason")
    val reason: String? = null,
)
