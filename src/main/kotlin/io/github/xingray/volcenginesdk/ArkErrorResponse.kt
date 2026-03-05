package io.github.xingray.volcenginesdk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * API 错误响应的包装类。
 *
 * 当 API 返回错误时，响应体会被反序列化为此对象。
 */
@Serializable
data class ArkErrorResponse(
    /**
     * 错误详情对象
     */
    @SerialName("error")
    val error: ArkError? = null,
)

/**
 * API 错误详情。
 *
 * @property code 错误码
 * @property message 错误描述
 * @property type 错误类型
 * @property param 导致错误的参数名
 */
@Serializable
data class ArkError(
    /**
     * 错误码
     */
    @SerialName("code")
    val code: String? = null,

    /**
     * 错误描述信息
     */
    @SerialName("message")
    val message: String? = null,

    /**
     * 错误类型
     */
    @SerialName("type")
    val type: String? = null,

    /**
     * 导致错误的参数名
     */
    @SerialName("param")
    val param: String? = null,
)
