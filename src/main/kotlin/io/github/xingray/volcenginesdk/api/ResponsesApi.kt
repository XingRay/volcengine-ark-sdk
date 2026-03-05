package io.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.ArkConstants

import io.github.xingray.volcenginesdk.ArkClient
import io.github.xingray.volcenginesdk.model.responses.*
import io.github.xingray.volcenginesdk.util.checkSuccess
import io.github.xingray.volcenginesdk.util.toSseFlow
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

/**
 * Responses API（新版推荐 API）。
 *
 * 支持文本生成、多模态理解、深度思考、工具调用（联网搜索/函数调用/MCP/图像处理）、
 * 上下文缓存、结构化输出等功能。
 *
 * API 端点:
 * - POST   /responses - 创建 Response
 * - GET    /responses/{responseId} - 获取 Response
 * - DELETE /responses/{responseId} - 删除 Response
 * - GET    /responses/{responseId}/input_items - 列出输入项
 *
 * @param client ArkClient 实例
 */
class ResponsesApi(private val client: ArkClient) {

    /**
     * 从纯文本创建 Response（非流式）。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param input 用户输入文本
     * @param instructions 系统指令
     * @param maxOutputTokens 最大输出 token 数
     * @param tools 工具列表
     * @param temperature 采样温度
     * @return Response 对象
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createResponseFromText(
        apiKey: String,
        model: String,
        input: String,
        instructions: String? = null,
        maxOutputTokens: Long? = null,
        tools: List<ResponsesTool>? = null,
        temperature: Double? = null,
    ): ResponseObject {
        val request = CreateResponsesRequest(
            model = model,
            input = JsonPrimitive(input),
            instructions = instructions,
            maxOutputTokens = maxOutputTokens,
            tools = tools,
            temperature = temperature,
        )
        return createResponse(apiKey, request)
    }

    /**
     * 从消息列表创建 Response（非流式）。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param input 消息列表的 JSON 表示
     * @param instructions 系统指令
     * @param maxOutputTokens 最大输出 token 数
     * @param tools 工具列表
     * @param temperature 采样温度
     * @param previousResponseId 上一轮 Response ID
     * @return Response 对象
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createResponseFromMessages(
        apiKey: String,
        model: String,
        input: JsonElement,
        instructions: String? = null,
        maxOutputTokens: Long? = null,
        tools: List<ResponsesTool>? = null,
        temperature: Double? = null,
        previousResponseId: String? = null,
    ): ResponseObject {
        val request = CreateResponsesRequest(
            model = model,
            input = input,
            instructions = instructions,
            maxOutputTokens = maxOutputTokens,
            tools = tools,
            temperature = temperature,
            previousResponseId = previousResponseId,
        )
        return createResponse(apiKey, request)
    }

    /**
     * 创建 Response（通用方法，非流式）。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 完整的请求参数
     * @return Response 对象
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createResponse(apiKey: String, request: CreateResponsesRequest): ResponseObject {
        val req = if (request.stream == true) request.copy(stream = false) else request
        val response = client.post(apiKey, ArkConstants.Endpoint.RESPONSES, req)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 从纯文本创建 Response（流式）。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param input 用户输入文本
     * @param instructions 系统指令
     * @param maxOutputTokens 最大输出 token 数
     * @param tools 工具列表
     * @param temperature 采样温度
     * @return 包含流式事件的 Flow
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun streamResponseFromText(
        apiKey: String,
        model: String,
        input: String,
        instructions: String? = null,
        maxOutputTokens: Long? = null,
        tools: List<ResponsesTool>? = null,
        temperature: Double? = null,
    ): Flow<ResponsesStreamEvent> {
        val request = CreateResponsesRequest(
            model = model,
            input = JsonPrimitive(input),
            instructions = instructions,
            maxOutputTokens = maxOutputTokens,
            tools = tools,
            temperature = temperature,
            stream = true,
        )
        return streamResponse(apiKey, request)
    }

    /**
     * 从消息列表创建 Response（流式）。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param input 消息列表的 JSON 表示
     * @param instructions 系统指令
     * @param maxOutputTokens 最大输出 token 数
     * @param tools 工具列表
     * @param temperature 采样温度
     * @param previousResponseId 上一轮 Response ID
     * @return 包含流式事件的 Flow
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun streamResponseFromMessages(
        apiKey: String,
        model: String,
        input: JsonElement,
        instructions: String? = null,
        maxOutputTokens: Long? = null,
        tools: List<ResponsesTool>? = null,
        temperature: Double? = null,
        previousResponseId: String? = null,
    ): Flow<ResponsesStreamEvent> {
        val request = CreateResponsesRequest(
            model = model,
            input = input,
            instructions = instructions,
            maxOutputTokens = maxOutputTokens,
            tools = tools,
            temperature = temperature,
            previousResponseId = previousResponseId,
            stream = true,
        )
        return streamResponse(apiKey, request)
    }

    /**
     * 创建 Response（通用方法，流式）。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 完整的请求参数
     * @return 包含流式事件的 Flow
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun streamResponse(apiKey: String, request: CreateResponsesRequest): Flow<ResponsesStreamEvent> {
        val req = request.copy(stream = true)
        val response = client.postStream(apiKey, ArkConstants.Endpoint.RESPONSES, req)
        response.checkSuccess()
        return response.toSseFlow().map { data ->
            client.json.decodeFromString<ResponsesStreamEvent>(data)
        }
    }

    /**
     * 获取已存储的 Response。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param responseId Response 唯一标识
     * @return Response 对象
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun getResponse(apiKey: String, responseId: String): ResponseObject {
        val response = client.get(apiKey, "${ArkConstants.Endpoint.RESPONSES}/$responseId")
        response.checkSuccess()
        return response.body()
    }

    /**
     * 删除已存储的 Response。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param responseId Response 唯一标识
     * @return 删除结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun deleteResponse(apiKey: String, responseId: String): DeleteResponseResponse {
        val response = client.delete(apiKey, "${ArkConstants.Endpoint.RESPONSES}/$responseId")
        response.checkSuccess()
        return response.body()
    }

    /**
     * 列出 Response 的输入项。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param responseId Response 唯一标识
     * @param limit 每页返回的最大数量
     * @param after 分页游标
     * @param before 分页游标
     * @param order 排序方式
     * @return 输入项列表响应
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun listResponseInputItems(
        apiKey: String,
        responseId: String,
        limit: Int? = null,
        after: String? = null,
        before: String? = null,
        order: String? = null,
    ): ListInputItemsResponse {
        val response = client.get(apiKey, "${ArkConstants.Endpoint.RESPONSES}/$responseId/input_items") {
            limit?.let { parameter("limit", it.toString()) }
            after?.let { parameter("after", it) }
            before?.let { parameter("before", it) }
            order?.let { parameter("order", it) }
        }
        response.checkSuccess()
        return response.body()
    }

    /**
     * 创建带联网搜索工具的 Response。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param input 用户输入（JsonElement）
     * @param maxKeyword 最大搜索关键词数
     * @param searchRecencyFilter 搜索时间过滤
     * @param thinking 深度思考配置
     * @return Response 对象
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createResponseWithWebSearch(
        apiKey: String,
        model: String,
        input: JsonElement,
        maxKeyword: Int? = null,
        searchRecencyFilter: String? = null,
        thinking: ResponsesThinking? = null,
    ): ResponseObject {
        val webSearchTool = ResponsesTool(
            type = "web_search",
            maxKeyword = maxKeyword,
            searchRecencyFilter = searchRecencyFilter,
        )
        return createResponse(
            apiKey,
            CreateResponsesRequest(
                model = model,
                input = input,
                tools = listOf(webSearchTool),
                thinking = thinking,
            )
        )
    }

    /**
     * 创建带函数调用工具的 Response。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param model 模型 ID，如 "doubao-seed-1.6-250615"
     * @param input 用户输入（JsonElement）
     * @param functions 函数工具列表
     * @param thinking 深度思考配置
     * @return Response 对象
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createResponseWithFunctions(
        apiKey: String,
        model: String,
        input: JsonElement,
        functions: List<ResponsesTool>,
        thinking: ResponsesThinking? = null,
    ): ResponseObject {
        return createResponse(
            apiKey,
            CreateResponsesRequest(
                model = model,
                input = input,
                tools = functions,
                thinking = thinking,
            )
        )
    }
}
