package com.github.xingray.volcenginesdk.util

import com.github.xingray.volcenginesdk.ArkErrorResponse
import com.github.xingray.volcenginesdk.ArkException
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * 检查 HTTP 响应状态，如果非成功则抛出 [ArkException]。
 *
 * 尝试从响应体中解析错误信息。如果无法解析，则使用 HTTP 状态码作为错误描述。
 *
 * @throws ArkException 当 HTTP 状态码不是 2xx 时
 */
internal suspend fun HttpResponse.checkSuccess() {
    if (!status.isSuccess()) {
        val error = try {
            body<ArkErrorResponse>()
        } catch (_: Exception) {
            null
        }
        throw ArkException(
            code = error?.error?.code,
            message = error?.error?.message ?: "HTTP ${status.value}",
            httpStatusCode = status.value,
        )
    }
}
