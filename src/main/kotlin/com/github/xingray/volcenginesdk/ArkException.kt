package com.github.xingray.volcenginesdk

/**
 * 火山方舟 API 调用异常。
 *
 * 当 API 返回非成功状态码或业务错误时抛出此异常。
 *
 * @param code 错误码，对应 API 返回的 error.code
 * @param message 错误描述信息
 * @param requestId 请求唯一标识，用于问题排查
 * @param httpStatusCode HTTP 状态码
 */
class ArkException(
    val code: String?,
    override val message: String?,
    val requestId: String? = null,
    val httpStatusCode: Int? = null,
) : RuntimeException(message)
