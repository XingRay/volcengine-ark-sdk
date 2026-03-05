package io.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 列出输入项的响应结果。
 *
 * @property data 输入项列表
 * @property objectType 对象类型
 * @property firstId 第一个项的 ID
 * @property lastId 最后一个项的 ID
 * @property hasMore 是否还有更多数据
 */
@Serializable
data class ListInputItemsResponse(
    @SerialName("data")
    val data: List<OutputItem> = emptyList(),

    @SerialName("object")
    val objectType: String? = null,

    @SerialName("first_id")
    val firstId: String? = null,

    @SerialName("last_id")
    val lastId: String? = null,

    @SerialName("has_more")
    val hasMore: Boolean = false,
)
