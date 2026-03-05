package io.github.xingray.volcenginesdk.model.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 内容生成任务的用量统计。
 *
 * @property completionTokens 输出消耗的 token 数量
 */
@Serializable
data class ContentGenerationUsage(
    @SerialName("completion_tokens")
    val completionTokens: Int = 0,
)
