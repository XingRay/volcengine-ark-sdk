package io.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.ArkClient
import io.github.xingray.volcenginesdk.ArkConstants
import io.github.xingray.volcenginesdk.model.chat.*
import io.github.xingray.volcenginesdk.util.checkSuccess
import io.github.xingray.volcenginesdk.util.toSseFlow
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 文本生成 API。
 *
 * 基于 Chat Completion 接口，支持文本生成、多轮对话、流式输出、深度思考、
 * 续写模式、函数调用、结构化输出等功能。
 *
 * API 端点: POST /chat/completions
 *
 * @param client ArkClient 实例
 */
class ChatCompletionApi(private val client: ArkClient) {

    /**
     * 创建文本生成（非流式）。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 文本生成请求参数
     * @return 文本生成响应结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createChatCompletion(apiKey: String, request: ChatCompletionRequest): ChatCompletionResult {
        val req = if (request.stream == true) request.copy(stream = false) else request
        val response = client.post(apiKey, ArkConstants.Endpoint.CHAT_COMPLETIONS, req)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 创建文本生成（流式）。
     *
     * 通过 SSE（Server-Sent Events）协议逐块接收模型输出。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 文本生成请求参数
     * @return 包含流式数据块的 Flow
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun streamChatCompletion(apiKey: String, request: ChatCompletionRequest): Flow<ChatCompletionChunk> {
        val req = request.copy(stream = true)
        val response = client.postStream(apiKey, ArkConstants.Endpoint.CHAT_COMPLETIONS, req)
        response.checkSuccess()
        return response.toSseFlow().map { data ->
            client.json.decodeFromString<ChatCompletionChunk>(data)
        }
    }

    /**
     * 创建深度思考文本生成（非流式）。
     *
     * 启用 thinking 模式，模型会先进行推理分析再生成最终回复。
     * 推理过程会在 reasoningContent 字段中返回。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param messages 对话消息列表
     * @param thinkingType 思考模式类型，默认 "enabled"
     * @param maxTokens 生成的最大 token 数
     * @return 文本生成响应结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createChatCompletionWithThinking(
        apiKey: String,
        model: String,
        messages: List<ChatMessage>,
        thinkingType: String = "enabled",
        maxTokens: Int? = null,
    ): ChatCompletionResult {
        return createChatCompletion(
            apiKey,
            ChatCompletionRequest(
                model = model,
                messages = messages,
                thinking = Thinking(type = thinkingType),
                maxTokens = maxTokens,
            )
        )
    }

    /**
     * 创建深度思考文本生成（流式）。
     *
     * 启用 thinking 模式的流式版本，通过 SSE 协议逐块接收模型推理过程和最终输出。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param messages 对话消息列表
     * @param thinkingType 思考模式类型，默认 "enabled"
     * @param maxTokens 生成的最大 token 数
     * @return 包含流式数据块的 Flow
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun streamChatCompletionWithThinking(
        apiKey: String,
        model: String,
        messages: List<ChatMessage>,
        thinkingType: String = "enabled",
        maxTokens: Int? = null,
    ): Flow<ChatCompletionChunk> {
        return streamChatCompletion(
            apiKey,
            ChatCompletionRequest(
                model = model,
                messages = messages,
                thinking = Thinking(type = thinkingType),
                maxTokens = maxTokens,
            )
        )
    }

    /**
     * 续写模式文本生成（非流式）。
     *
     * 通过在消息列表末尾添加未完成的 assistant 消息来引导模型续写。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param messages 对话消息列表（末尾应为 assistant 消息的开头部分）
     * @param maxTokens 生成的最大 token 数
     * @param temperature 采样温度
     * @return 文本生成响应结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createPrefillChatCompletion(
        apiKey: String,
        model: String,
        messages: List<ChatMessage>,
        maxTokens: Int? = null,
        temperature: Double? = null,
    ): ChatCompletionResult {
        return createChatCompletion(
            apiKey,
            ChatCompletionRequest(
                model = model,
                messages = messages,
                maxTokens = maxTokens,
                temperature = temperature,
            )
        )
    }

    /**
     * 带函数调用的文本生成（非流式）。
     *
     * 允许模型在生成过程中调用预定义的工具/函数，实现与外部系统的交互。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param messages 对话消息列表
     * @param tools 可用的工具/函数列表
     * @param toolChoice 工具选择策略
     * @param maxTokens 生成的最大 token 数
     * @return 文本生成响应结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createChatCompletionWithTools(
        apiKey: String,
        model: String,
        messages: List<ChatMessage>,
        tools: List<ChatTool>,
        toolChoice: kotlinx.serialization.json.JsonElement? = null,
        maxTokens: Int? = null,
    ): ChatCompletionResult {
        return createChatCompletion(
            apiKey,
            ChatCompletionRequest(
                model = model,
                messages = messages,
                tools = tools,
                toolChoice = toolChoice,
                maxTokens = maxTokens,
            )
        )
    }

    /**
     * 带函数调用的文本生成（流式）。
     *
     * 允许模型在流式生成过程中调用预定义的工具/函数，通过 SSE 协议逐块接收输出。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param messages 对话消息列表
     * @param tools 可用的工具/函数列表
     * @param toolChoice 工具选择策略
     * @param maxTokens 生成的最大 token 数
     * @return 包含流式数据块的 Flow
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun streamChatCompletionWithTools(
        apiKey: String,
        model: String,
        messages: List<ChatMessage>,
        tools: List<ChatTool>,
        toolChoice: kotlinx.serialization.json.JsonElement? = null,
        maxTokens: Int? = null,
    ): Flow<ChatCompletionChunk> {
        return streamChatCompletion(
            apiKey,
            ChatCompletionRequest(
                model = model,
                messages = messages,
                tools = tools,
                toolChoice = toolChoice,
                maxTokens = maxTokens,
            )
        )
    }

    /**
     * 结构化输出文本生成（非流式）。
     *
     * 约束模型输出为指定的 JSON 格式。可通过 jsonSchema 参数指定具体的 JSON Schema，
     * 或不传 jsonSchema 使用 json_object 模式让模型自行决定 JSON 结构。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param messages 对话消息列表
     * @param jsonSchema JSON Schema 参数（传入则使用 json_schema 模式，否则使用 json_object 模式）
     * @param maxTokens 生成的最大 token 数
     * @return 文本生成响应结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createChatCompletionWithJsonOutput(
        apiKey: String,
        model: String,
        messages: List<ChatMessage>,
        jsonSchema: JsonSchemaParam? = null,
        maxTokens: Int? = null,
    ): ChatCompletionResult {
        val format = if (jsonSchema != null) {
            ResponseFormat(type = "json_schema", jsonSchema = jsonSchema)
        } else {
            ResponseFormat(type = "json_object")
        }
        return createChatCompletion(
            apiKey,
            ChatCompletionRequest(
                model = model,
                messages = messages,
                responseFormat = format,
                maxTokens = maxTokens,
            )
        )
    }
}
