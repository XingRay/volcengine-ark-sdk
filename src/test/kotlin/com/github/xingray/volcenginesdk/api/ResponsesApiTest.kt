package com.github.xingray.volcenginesdk.api

import com.github.xingray.volcenginesdk.model.responses.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlin.test.*

/**
 * ResponsesApi 集成测试。
 *
 * 使用真实 API 进行测试，需要设置环境变量 ARK_API_KEY。
 */
class ResponsesApiTest {

    private val apiKey get() = TestConfig.requireApiKey()
    private val model = "doubao-seed-1.6-250615"
    private val client = TestConfig.createClient()
    private val api = ResponsesApi(client)

    @AfterTest
    fun tearDown() {
        client.close()
    }

    // ========== createResponseFromText ==========

    @Test
    fun `createResponseFromText should return response`() = runTest {
        val result = api.createResponseFromText(
            apiKey = apiKey,
            model = model,
            input = "请回复：你好",
            maxOutputTokens = 50,
        )

        assertNotNull(result.id, "id 不应为空")
        assertEquals("completed", result.status, "status 应为 completed")
        assertNotNull(result.output, "output 不应为空")
        assertTrue(result.output.isNotEmpty(), "output 列表不应为空")
        assertNotNull(result.usage, "usage 不应为空")
        assertTrue(result.usage.totalTokens > 0, "totalTokens 应大于 0")

        println("========== createResponseFromText ==========")
        println("id: ${result.id}")
        println("model: ${result.model}")
        println("status: ${result.status}")
        result.output.forEachIndexed { index, item ->
            println("output #$index: type=${item.type}, role=${item.role}, status=${item.status}")
            item.content?.forEach { contentItem ->
                println("  content: type=${contentItem.type}, text=${contentItem.text}")
            }
        }
        println("usage: inputTokens=${result.usage.inputTokens}, outputTokens=${result.usage.outputTokens}, totalTokens=${result.usage.totalTokens}")
    }

    @Test
    fun `createResponseFromText with instructions should return response`() = runTest {
        val result = api.createResponseFromText(
            apiKey = apiKey,
            model = model,
            input = "介绍一下你自己",
            instructions = "你是一个友好的助手，请用简短的中文回复",
            maxOutputTokens = 100,
        )

        assertNotNull(result.id)
        assertEquals("completed", result.status)

        println("========== createResponseFromText (with instructions) ==========")
        println("id: ${result.id}")
        println("status: ${result.status}")
        result.output?.forEach { item ->
            item.content?.forEach { contentItem ->
                println("回复内容: ${contentItem.text}")
            }
        }
        println("usage: inputTokens=${result.usage?.inputTokens}, outputTokens=${result.usage?.outputTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    // ========== createResponseFromMessages ==========

    @Test
    fun `createResponseFromMessages should return response`() = runTest {
        val input = buildJsonArray {
            add(buildJsonObject {
                put("role", JsonPrimitive("user"))
                put("content", JsonPrimitive("1+1等于几？"))
            })
        }

        val result = api.createResponseFromMessages(
            apiKey = apiKey,
            model = model,
            input = input,
            maxOutputTokens = 50,
        )

        assertNotNull(result.id)
        assertEquals("completed", result.status)
        assertTrue(result.output!!.isNotEmpty())

        println("========== createResponseFromMessages ==========")
        println("id: ${result.id}")
        println("status: ${result.status}")
        result.output.forEach { item ->
            println("output: type=${item.type}, role=${item.role}")
            item.content?.forEach { contentItem ->
                println("  回复内容: ${contentItem.text}")
            }
        }
        println("usage: inputTokens=${result.usage?.inputTokens}, outputTokens=${result.usage?.outputTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    // ========== createResponse ==========

    @Test
    fun `createResponse should return response`() = runTest {
        val request = CreateResponsesRequest(
            model = model,
            input = JsonPrimitive("你好"),
            maxOutputTokens = 50,
        )
        val result = api.createResponse(apiKey, request)

        assertNotNull(result.id)
        assertEquals("completed", result.status)

        println("========== createResponse ==========")
        println("id: ${result.id}")
        println("model: ${result.model}")
        println("status: ${result.status}")
        result.output?.forEach { item ->
            item.content?.forEach { contentItem ->
                println("回复内容: ${contentItem.text}")
            }
        }
        println("usage: inputTokens=${result.usage?.inputTokens}, outputTokens=${result.usage?.outputTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    // ========== streamResponseFromText ==========

    @Test
    fun `streamResponseFromText should return Flow of events`() = runTest {
        val events = api.streamResponseFromText(
            apiKey = apiKey,
            model = model,
            input = "请回复：你好",
            maxOutputTokens = 50,
        ).toList()

        assertTrue(events.isNotEmpty(), "events 不应为空")
        assertTrue(events.any { it.type == "response.completed" }, "应包含 response.completed 事件")

        println("========== streamResponseFromText ==========")
        println("共 ${events.size} 个事件")
        val fullText = buildString {
            events.forEach { event ->
                if (event.type == "response.output_text.delta") {
                    append(event.delta ?: "")
                }
            }
        }
        println("事件类型列表:")
        events.forEach { event ->
            println("  - type=${event.type}, sequenceNumber=${event.sequenceNumber}")
        }
        println("拼接完整回复: $fullText")
        // 输出完成事件中的 usage
        val completedEvent = events.firstOrNull { it.type == "response.completed" }
        completedEvent?.response?.usage?.let { usage ->
            println("usage: inputTokens=${usage.inputTokens}, outputTokens=${usage.outputTokens}, totalTokens=${usage.totalTokens}")
        }
    }

    // ========== streamResponseFromMessages ==========

    @Test
    fun `streamResponseFromMessages should return Flow of events`() = runTest {
        val input = buildJsonArray {
            add(buildJsonObject {
                put("role", JsonPrimitive("user"))
                put("content", JsonPrimitive("你好"))
            })
        }

        val events = api.streamResponseFromMessages(
            apiKey = apiKey,
            model = model,
            input = input,
            maxOutputTokens = 50,
        ).toList()

        assertTrue(events.isNotEmpty(), "events 不应为空")

        println("========== streamResponseFromMessages ==========")
        println("共 ${events.size} 个事件")
        val fullText = buildString {
            events.forEach { event ->
                if (event.type == "response.output_text.delta") {
                    append(event.delta ?: "")
                }
            }
        }
        println("拼接完整回复: $fullText")
    }

    // ========== streamResponse ==========

    @Test
    fun `streamResponse should return Flow of events`() = runTest {
        val request = CreateResponsesRequest(
            model = model,
            input = JsonPrimitive("你好"),
            maxOutputTokens = 50,
        )
        val events = api.streamResponse(apiKey, request).toList()

        assertTrue(events.isNotEmpty(), "events 不应为空")

        println("========== streamResponse ==========")
        println("共 ${events.size} 个事件")
        val fullText = buildString {
            events.forEach { event ->
                if (event.type == "response.output_text.delta") {
                    append(event.delta ?: "")
                }
            }
        }
        println("拼接完整回复: $fullText")
        events.forEach { event ->
            println("  - type=${event.type}")
        }
    }

    // ========== getResponse ==========

    @Test
    fun `getResponse should return stored response`() = runTest {
        // 先创建一个 Response（需要 store=true）
        val createResult = api.createResponse(
            apiKey,
            CreateResponsesRequest(
                model = model,
                input = JsonPrimitive("你好"),
                maxOutputTokens = 50,
                store = true,
            ),
        )
        assertNotNull(createResult.id)

        // 获取 Response
        val getResult = api.getResponse(apiKey, createResult.id)

        assertEquals(createResult.id, getResult.id)
        assertNotNull(getResult.status)

        println("========== getResponse ==========")
        println("id: ${getResult.id}")
        println("model: ${getResult.model}")
        println("status: ${getResult.status}")
        println("store: ${getResult.store}")
        println("createdAt: ${getResult.createdAt}")
        getResult.output?.forEach { item ->
            println("output: type=${item.type}, role=${item.role}")
            item.content?.forEach { contentItem ->
                println("  回复内容: ${contentItem.text}")
            }
        }
        println("usage: inputTokens=${getResult.usage?.inputTokens}, outputTokens=${getResult.usage?.outputTokens}, totalTokens=${getResult.usage?.totalTokens}")
    }

    // ========== deleteResponse ==========

    @Test
    fun `deleteResponse should delete stored response`() = runTest {
        // 先创建一个 Response（需要 store=true）
        val createResult = api.createResponse(
            apiKey,
            CreateResponsesRequest(
                model = model,
                input = JsonPrimitive("测试删除"),
                maxOutputTokens = 50,
                store = true,
            ),
        )
        assertNotNull(createResult.id)

        // 删除 Response
        val deleteResult = api.deleteResponse(apiKey, createResult.id)

        assertEquals(createResult.id, deleteResult.id)
        assertTrue(deleteResult.deleted, "deleted 应为 true")

        println("========== deleteResponse ==========")
        println("id: ${deleteResult.id}")
        println("deleted: ${deleteResult.deleted}")
    }

    // ========== listResponseInputItems ==========

    @Test
    fun `listResponseInputItems should return input items`() = runTest {
        // 先创建一个 Response（需要 store=true）
        val createResult = api.createResponse(
            apiKey,
            CreateResponsesRequest(
                model = model,
                input = JsonPrimitive("你好"),
                maxOutputTokens = 50,
                store = true,
            ),
        )
        assertNotNull(createResult.id)

        // 列出输入项
        val inputItems = api.listResponseInputItems(apiKey, createResult.id)

        assertNotNull(inputItems.data, "data 不应为空")

        println("========== listResponseInputItems ==========")
        println("responseId: ${createResult.id}")
        println("共 ${inputItems.data.size} 条输入项, hasMore=${inputItems.hasMore}")
        inputItems.data.forEachIndexed { index, item ->
            println("输入项 #$index:")
            println("  id: ${item.id}")
            println("  type: ${item.type}")
            println("  role: ${item.role}")
            println("  status: ${item.status}")
            item.content?.forEach { contentItem ->
                println("  content: type=${contentItem.type}, text=${contentItem.text}")
            }
        }

        // 清理
        api.deleteResponse(apiKey, createResult.id)
    }

    // ========== createResponseWithWebSearch ==========

    @Test
    fun `createResponseWithWebSearch should return response with search results`() = runTest {
        val input = buildJsonArray {
            add(buildJsonObject {
                put("role", JsonPrimitive("user"))
                put("content", JsonPrimitive("今天北京天气怎么样？"))
            })
        }

        val result = api.createResponseWithWebSearch(
            apiKey = apiKey,
            model = model,
            input = input,
        )

        assertNotNull(result.id)
        assertEquals("completed", result.status)
        assertNotNull(result.output)
        assertTrue(result.output.isNotEmpty())

        println("========== createResponseWithWebSearch ==========")
        println("id: ${result.id}")
        println("status: ${result.status}")
        result.output.forEachIndexed { index, item ->
            println("output #$index: type=${item.type}, status=${item.status}")
            if (item.type == "web_search_call") {
                println("  搜索调用 id: ${item.id}")
            }
            item.content?.forEach { contentItem ->
                println("  content: type=${contentItem.type}")
                println("  text: ${contentItem.text?.take(200)}${if ((contentItem.text?.length ?: 0) > 200) "..." else ""}")
                contentItem.annotations?.forEach { annotation ->
                    println("  annotation: $annotation")
                }
            }
        }
        println("usage: inputTokens=${result.usage?.inputTokens}, outputTokens=${result.usage?.outputTokens}, totalTokens=${result.usage?.totalTokens}")
    }

    // ========== createResponseWithFunctions ==========

    @Test
    fun `createResponseWithFunctions should return response with function call`() = runTest {
        val functions = listOf(
            ResponsesTool(
                type = "function",
                name = "get_weather",
                description = "获取指定城市的当前天气信息",
                parameters = buildJsonObject {
                    put("type", JsonPrimitive("object"))
                    put("properties", buildJsonObject {
                        put("city", buildJsonObject {
                            put("type", JsonPrimitive("string"))
                            put("description", JsonPrimitive("城市名称"))
                        })
                    })
                    put("required", buildJsonArray { add(JsonPrimitive("city")) })
                },
            ),
        )

        val input = buildJsonArray {
            add(buildJsonObject {
                put("role", JsonPrimitive("user"))
                put("content", JsonPrimitive("北京今天天气怎么样？"))
            })
        }

        val result = api.createResponseWithFunctions(
            apiKey = apiKey,
            model = model,
            input = input,
            functions = functions,
        )

        assertNotNull(result.id)
        assertNotNull(result.output)
        assertTrue(result.output.isNotEmpty())

        println("========== createResponseWithFunctions ==========")
        println("id: ${result.id}")
        println("status: ${result.status}")
        result.output.forEachIndexed { index, item ->
            println("output #$index:")
            println("  type: ${item.type}")
            println("  status: ${item.status}")
            if (item.type == "function_call") {
                println("  函数名: ${item.name}")
                println("  callId: ${item.callId}")
                println("  参数: ${item.arguments}")
            }
            item.content?.forEach { contentItem ->
                println("  content: type=${contentItem.type}, text=${contentItem.text}")
            }
        }
        println("usage: inputTokens=${result.usage?.inputTokens}, outputTokens=${result.usage?.outputTokens}, totalTokens=${result.usage?.totalTokens}")
    }
}
