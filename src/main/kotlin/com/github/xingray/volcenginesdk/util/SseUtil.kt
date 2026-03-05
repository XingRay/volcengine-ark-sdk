package com.github.xingray.volcenginesdk.util

import io.ktor.client.statement.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 将 HTTP 响应转换为 SSE（Server-Sent Events）数据流。
 *
 * 解析 SSE 协议格式，提取 "data: " 前缀后的内容，
 * 遇到 "[DONE]" 标记时终止流。
 *
 * @return 包含每个 SSE data 事件内容的 Flow
 */
fun HttpResponse.toSseFlow(): Flow<String> = flow {
    val channel: ByteReadChannel = bodyAsChannel()
    while (!channel.isClosedForRead) {
        val line = channel.readLine() ?: break
        if (line.startsWith("data: ")) {
            val data = line.removePrefix("data: ").trim()
            if (data == "[DONE]") break
            emit(data)
        }
    }
}
