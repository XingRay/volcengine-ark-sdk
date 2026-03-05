package com.github.xingray.volcenginesdk.api

import com.github.xingray.volcenginesdk.ArkConstants

import com.github.xingray.volcenginesdk.ArkClient
import com.github.xingray.volcenginesdk.model.chat.*
import com.github.xingray.volcenginesdk.model.context.*
import com.github.xingray.volcenginesdk.util.checkSuccess
import com.github.xingray.volcenginesdk.util.toSseFlow
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 上下文缓存 API。
 *
 * 支持创建可复用的上下文缓存，避免重复传输相同的上下文内容，
 * 降低 token 消耗和请求延迟。缓存创建后可在多次对话中引用。
 *
 * API 端点:
 * - POST /context/create - 创建上下文缓存
 * - POST /chat/completions - 使用上下文缓存进行对话（通过 context 参数引用）
 *
 * @param client ArkClient 实例
 */
class ContextApi(private val client: ArkClient) {

    /**
     * 创建上下文缓存。
     *
     * 将一组消息或文档内容缓存到服务端，返回上下文缓存 ID。
     * 后续对话可通过该 ID 引用缓存内容，无需重复传输。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 创建上下文缓存的请求参数
     * @return 上下文缓存创建结果，包含缓存 ID 和元数据
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createContext(apiKey: String, request: CreateContextRequest): CreateContextResult {
        val response = client.post(apiKey, ArkConstants.Endpoint.CONTEXT_CREATE, request)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 使用上下文缓存创建文本生成（非流式）。
     *
     * 在对话请求中引用已创建的上下文缓存，减少重复 token 消耗。
     * 模型会将缓存内容作为上下文的一部分参与生成。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 文本生成请求参数（应包含 context 引用）
     * @return 文本生成响应结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createContextChatCompletion(apiKey: String, request: ChatCompletionRequest): ChatCompletionResult {
        val req = if (request.stream == true) request.copy(stream = false) else request
        val response = client.post(apiKey, ArkConstants.Endpoint.CHAT_COMPLETIONS, req)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 使用上下文缓存创建文本生成（流式）。
     *
     * 在流式对话请求中引用已创建的上下文缓存，
     * 通过 SSE（Server-Sent Events）协议逐块接收模型输出。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 文本生成请求参数（应包含 context 引用）
     * @return 包含流式数据块的 Flow
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun streamContextChatCompletion(apiKey: String, request: ChatCompletionRequest): Flow<ChatCompletionChunk> {
        val req = request.copy(stream = true)
        val response = client.postStream(apiKey, ArkConstants.Endpoint.CHAT_COMPLETIONS, req)
        response.checkSuccess()
        return response.toSseFlow().map { data ->
            client.json.decodeFromString<ChatCompletionChunk>(data)
        }
    }
}
