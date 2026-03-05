package com.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.ArkConstants

import io.github.xingray.volcenginesdk.ArkClient
import io.github.xingray.volcenginesdk.model.content.*
import io.github.xingray.volcenginesdk.util.checkSuccess
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * 内容生成任务 API（视频生成、3D 生成）。
 *
 * 采用异步任务模式：提交任务 -> 查询状态 -> 获取结果。
 *
 * API 端点:
 * - POST   /contents/generations/tasks - 提交任务
 * - GET    /contents/generations/tasks/{taskId} - 查询任务
 * - GET    /contents/generations/tasks - 列出任务
 * - DELETE /contents/generations/tasks/{taskId} - 删除任务
 *
 * @param client ArkClient 实例
 */
class ContentGenerationApi(private val client: ArkClient) {

    /**
     * 提交内容生成任务。
     *
     * 创建一个异步内容生成任务（如视频生成、3D 模型生成等），
     * 任务创建后需要通过 [getTask] 或 [awaitTask] 查询任务状态和获取结果。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 创建任务请求参数
     * @return 任务创建结果（包含任务 ID）
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun createTask(apiKey: String, request: CreateContentGenerationTaskRequest): CreateContentGenerationTaskResult {
        val response = client.post(apiKey, ArkConstants.Endpoint.CONTENT_GENERATION_TASKS, request)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 查询内容生成任务状态。
     *
     * 根据任务 ID 获取任务的当前状态和结果信息。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param taskId 任务唯一标识
     * @return 任务详情（包含状态和结果）
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun getTask(apiKey: String, taskId: String): GetContentGenerationTaskResponse {
        val response = client.get(apiKey, "${ArkConstants.Endpoint.CONTENT_GENERATION_TASKS}/$taskId")
        response.checkSuccess()
        return response.body()
    }

    /**
     * 轮询查询任务直到完成。
     *
     * 自动轮询任务状态，直到任务成功（succeeded）或失败（failed）。
     * 适用于需要等待任务完成后再继续处理的场景。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param taskId 任务唯一标识
     * @param pollIntervalMs 轮询间隔（毫秒），默认 3000
     * @return 最终的任务结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun awaitTask(apiKey: String, taskId: String, pollIntervalMs: Long = 3000): GetContentGenerationTaskResponse {
        while (true) {
            val result = getTask(apiKey, taskId)
            when (result.status) {
                "succeeded", "failed" -> return result
                else -> kotlinx.coroutines.delay(pollIntervalMs)
            }
        }
    }

    /**
     * 列出内容生成任务。
     *
     * 分页查询已提交的内容生成任务，支持按模型和状态筛选。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param pageNum 页码，从 1 开始
     * @param pageSize 每页数量
     * @param model 按模型 ID 筛选
     * @param status 按任务状态筛选（如 "running"、"succeeded"、"failed"）
     * @return 任务列表响应，包含分页信息和任务列表
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun listTasks(
        apiKey: String,
        pageNum: Int? = null,
        pageSize: Int? = null,
        model: String? = null,
        status: String? = null,
    ): ListContentGenerationTasksResponse {
        val response = client.get(apiKey, ArkConstants.Endpoint.CONTENT_GENERATION_TASKS) {
            pageNum?.let { parameter("page_num", it.toString()) }
            pageSize?.let { parameter("page_size", it.toString()) }
            model?.let { parameter("model", it) }
            status?.let { parameter("status", it) }
        }
        response.checkSuccess()
        return response.body()
    }

    /**
     * 删除内容生成任务。
     *
     * 根据任务 ID 删除指定的内容生成任务及其关联的结果数据。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param taskId 任务唯一标识
     * @return 删除结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun deleteTask(apiKey: String, taskId: String): DeleteContentGenerationTaskResponse {
        val response = client.delete(apiKey, "${ArkConstants.Endpoint.CONTENT_GENERATION_TASKS}/$taskId")
        response.checkSuccess()
        return response.body()
    }
}
