package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Responses API 推理配置。
 *
 * @property effort 推理力度
 * @property summary 推理摘要模式
 */
@Serializable
data class ResponsesReasoning(
    @SerialName("effort")
    val effort: String? = null,

    @SerialName("summary")
    val summary: String? = null,
)
