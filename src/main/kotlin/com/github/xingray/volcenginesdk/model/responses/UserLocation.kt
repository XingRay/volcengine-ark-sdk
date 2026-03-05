package com.github.xingray.volcenginesdk.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 用户位置信息。
 *
 * @property type 位置类型
 * @property city 城市
 * @property country 国家
 * @property region 地区
 * @property timezone 时区
 */
@Serializable
data class UserLocation(
    @SerialName("type")
    val type: String? = null,

    @SerialName("city")
    val city: String? = null,

    @SerialName("country")
    val country: String? = null,

    @SerialName("region")
    val region: String? = null,

    @SerialName("timezone")
    val timezone: String? = null,
)
