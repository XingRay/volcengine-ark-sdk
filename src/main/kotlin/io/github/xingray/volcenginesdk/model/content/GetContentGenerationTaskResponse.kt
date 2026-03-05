package io.github.xingray.volcenginesdk.model.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 查询内容生成任务的响应结果。
 *
 * @property id 任务唯一标识
 * @property model 使用的模型标识
 * @property status 任务状态：running/succeeded/failed
 * @property content 生成的内容
 * @property error 错误信息
 * @property usage 用量统计
 * @property createdAt 创建时间戳
 * @property updatedAt 更新时间戳
 */
@Serializable
data class GetContentGenerationTaskResponse(
    @SerialName("id")
    val id: String? = null,

    @SerialName("model")
    val model: String? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("content")
    val content: ContentGenerationContent? = null,

    @SerialName("error")
    val error: ContentGenerationError? = null,

    @SerialName("usage")
    val usage: ContentGenerationUsage? = null,

    @SerialName("created_at")
    val createdAt: Long = 0,

    @SerialName("updated_at")
    val updatedAt: Long = 0,
)
