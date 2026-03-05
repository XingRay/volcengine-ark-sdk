package io.github.xingray.volcenginesdk.model.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 列出内容生成任务的响应结果。
 *
 * @property data 任务列表
 * @property total 总数量
 * @property pageNum 当前页码
 * @property pageSize 每页数量
 */
@Serializable
data class ListContentGenerationTasksResponse(
    @SerialName("data")
    val data: List<GetContentGenerationTaskResponse> = emptyList(),

    @SerialName("total")
    val total: Int = 0,

    @SerialName("page_num")
    val pageNum: Int = 0,

    @SerialName("page_size")
    val pageSize: Int = 0,
)
