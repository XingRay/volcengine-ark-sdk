package io.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.ArkClient
import io.github.xingray.volcenginesdk.ArkConstants
import io.github.xingray.volcenginesdk.model.bot.*
import io.github.xingray.volcenginesdk.util.checkSuccess
import io.github.xingray.volcenginesdk.util.toSseFlow
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Bot 对话 API。
 *
 * 基于预配置的 Bot（智能体）进行对话，Bot 可包含预设的系统提示词、
 * 知识库、插件等配置，简化应用端的调用逻辑。
 *
 * API 端点: POST /bots/chat/completions
 *
 * @param client ArkClient 实例
 */
class BotChatApi(private val client: ArkClient) {

    /**
     * 创建 Bot 对话（非流式）。
     *
     * 使用预配置的 Bot 进行对话生成。Bot 已封装了系统提示词、知识库检索、
     * 插件调用等逻辑，调用方只需提供用户消息即可。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request Bot 对话请求参数，包含 Bot ID 和消息列表
     * @return Bot 对话响应结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createBotChatCompletion(apiKey: String, request: BotChatCompletionRequest): BotChatCompletionResult {
        val req = if (request.stream == true) request.copy(stream = false) else request
        val response = client.post(apiKey, ArkConstants.Endpoint.BOT_CHAT_COMPLETIONS, req)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 创建 Bot 对话（流式）。
     *
     * 使用预配置的 Bot 进行流式对话生成，通过 SSE（Server-Sent Events）
     * 协议逐块接收 Bot 的输出，适用于需要实时展示回复的场景。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request Bot 对话请求参数，包含 Bot ID 和消息列表
     * @return 包含流式数据块的 Flow
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun streamBotChatCompletion(apiKey: String, request: BotChatCompletionRequest): Flow<BotChatCompletionChunk> {
        val req = request.copy(stream = true)
        val response = client.postStream(apiKey, ArkConstants.Endpoint.BOT_CHAT_COMPLETIONS, req)
        response.checkSuccess()
        return response.toSseFlow().map { data ->
            client.json.decodeFromString<BotChatCompletionChunk>(data)
        }
    }
}
