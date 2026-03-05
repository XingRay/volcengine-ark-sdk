package io.github.xingray.volcenginesdk.model.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 创建内容生成任务请求参数。
 *
 * 用于提交视频生成、3D 生成等异步任务。
 *
 * @property model 模型 ID，如 "doubao-seedance-1-0-lite"
 * @property content 内容项列表
 * @property serviceTier 服务层级
 */
@Serializable
data class CreateContentGenerationTaskRequest(
    @SerialName("model")
    val model: String,

    @SerialName("content")
    val content: List<ContentItem>,

    @SerialName("service_tier")
    val serviceTier: String? = null,
)
