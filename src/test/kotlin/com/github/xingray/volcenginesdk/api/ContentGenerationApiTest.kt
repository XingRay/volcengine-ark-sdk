package com.github.xingray.volcenginesdk.api

import com.github.xingray.volcenginesdk.model.content.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

/**
 * ContentGenerationApi 集成测试。
 *
 * 使用真实 API 进行测试，需要设置环境变量 ARK_API_KEY。
 */
class ContentGenerationApiTest {

    private val apiKey get() = TestConfig.requireApiKey()
    private val model = "doubao-seedance-1-0-lite"
    private val client = TestConfig.createClient()
    private val api = ContentGenerationApi(client)

    @AfterTest
    fun tearDown() {
        client.close()
    }

    // ========== createTask ==========

    @Test
    fun `createTask should return task with running status`() = runTest {
        val result = api.createTask(
            apiKey,
            CreateContentGenerationTaskRequest(
                model = model,
                content = listOf(ContentItem(type = "text", text = "一只猫在草地上奔跑")),
            ),
        )

        assertNotNull(result.id, "任务 id 不应为空")
        assertNotNull(result.status, "任务 status 不应为空")

        println("========== createTask ==========")
        println("taskId: ${result.id}")
        println("model: ${result.model}")
        println("status: ${result.status}")
        println("createdAt: ${result.createdAt}")
        println("updatedAt: ${result.updatedAt}")
    }

    // ========== getTask ==========

    @Test
    fun `getTask should return task details`() = runTest {
        // 先创建一个任务
        val createResult = api.createTask(
            apiKey,
            CreateContentGenerationTaskRequest(
                model = model,
                content = listOf(ContentItem(type = "text", text = "一只猫在散步")),
            ),
        )
        assertNotNull(createResult.id)

        // 查询任务状态
        val taskDetail = api.getTask(apiKey, createResult.id)

        assertEquals(createResult.id, taskDetail.id)
        assertNotNull(taskDetail.status, "status 不应为空")

        println("========== getTask ==========")
        println("taskId: ${taskDetail.id}")
        println("model: ${taskDetail.model}")
        println("status: ${taskDetail.status}")
        println("createdAt: ${taskDetail.createdAt}")
        println("updatedAt: ${taskDetail.updatedAt}")
        if (taskDetail.content != null) {
            println("videoUrl: ${taskDetail.content.videoUrl ?: "(无)"}")
            println("modelUrl: ${taskDetail.content.modelUrl ?: "(无)"}")
            println("coverImageUrl: ${taskDetail.content.coverImageUrl ?: "(无)"}")
        }
        if (taskDetail.error != null) {
            println("error: ${taskDetail.error}")
        }
    }

    // ========== awaitTask ==========

    @Test
    fun `awaitTask should wait until task completes`() = runTest {
        val createResult = api.createTask(
            apiKey,
            CreateContentGenerationTaskRequest(
                model = model,
                content = listOf(ContentItem(type = "text", text = "一只猫在阳光下")),
            ),
        )

        val finalResult = api.awaitTask(apiKey, createResult.id, pollIntervalMs = 5000)

        assertTrue(
            finalResult.status == "succeeded" || finalResult.status == "failed",
            "最终状态应为 succeeded 或 failed，实际: ${finalResult.status}"
        )

        println("========== awaitTask ==========")
        println("taskId: ${finalResult.id}")
        println("status: ${finalResult.status}")
        println("createdAt: ${finalResult.createdAt}")
        println("updatedAt: ${finalResult.updatedAt}")
        if (finalResult.status == "succeeded" && finalResult.content != null) {
            println("videoUrl: ${finalResult.content.videoUrl ?: "(无)"}")
            println("modelUrl: ${finalResult.content.modelUrl ?: "(无)"}")
            println("coverImageUrl: ${finalResult.content.coverImageUrl ?: "(无)"}")
        }
        if (finalResult.error != null) {
            println("error: ${finalResult.error}")
        }
        if (finalResult.usage != null) {
            println("usage: completionTokens=${finalResult.usage.completionTokens}")
        }
    }

    // ========== listTasks ==========

    @Test
    fun `listTasks should return task list`() = runTest {
        val result = api.listTasks(apiKey, pageNum = 1, pageSize = 5)

        assertNotNull(result.data, "data 不应为空")
        assertTrue(result.pageSize > 0, "pageSize 应大于 0")

        println("========== listTasks ==========")
        println("total: ${result.total}")
        println("pageSize: ${result.pageSize}")
        println("当前页任务数: ${result.data.size}")
        result.data.forEachIndexed { index, task ->
            println("任务 #$index:")
            println("  id: ${task.id}")
            println("  model: ${task.model}")
            println("  status: ${task.status}")
            if (task.content != null) {
                println("  videoUrl: ${task.content.videoUrl ?: "(无)"}")
                println("  modelUrl: ${task.content.modelUrl ?: "(无)"}")
                println("  coverImageUrl: ${task.content.coverImageUrl ?: "(无)"}")
            }
        }
    }

    // ========== deleteTask ==========

    @Test
    fun `deleteTask should delete task successfully`() = runTest {
        // 先创建一个任务
        val createResult = api.createTask(
            apiKey,
            CreateContentGenerationTaskRequest(
                model = model,
                content = listOf(ContentItem(type = "text", text = "测试删除任务")),
            ),
        )

        // 等待任务完成再删除
        api.awaitTask(apiKey, createResult.id, pollIntervalMs = 5000)

        // 删除任务
        val deleteResult = api.deleteTask(apiKey, createResult.id)

        assertEquals(createResult.id, deleteResult.id)
        assertTrue(deleteResult.deleted, "deleted 应为 true")

        println("========== deleteTask ==========")
        println("taskId: ${deleteResult.id}")
        println("deleted: ${deleteResult.deleted}")
    }
}
