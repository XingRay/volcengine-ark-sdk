package io.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.model.image.GenerateImagesRequest
import io.github.xingray.volcenginesdk.model.image.ResponseFormat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * ImageGenerationApi 集成测试。
 *
 * 使用真实 API 进行测试，需要设置环境变量 ARK_API_KEY。
 */
class ImageGenerationApiTest {

    private val apiKey get() = TestConfig.requireApiKey()
    private val model = "doubao-seedream-5-0-260128"
    private val client = TestConfig.createClient()
    private val api = ImageGenerationApi(client)

    @AfterTest
    fun tearDown() {
        client.close()
    }

    // ========== generateImagesAsUrl ==========

    @Test
    fun `generateImagesAsUrl should return image URL`() = runTest {
        val result = api.generateImagesAsUrl(
            apiKey,
            GenerateImagesRequest(model = model, prompt = "一只胖胖的橘猫跳起来抓飞在空中的毛线球"),
        )

        assertTrue(result.data.isNotEmpty(), "data 不应为空")
        assertNotNull(result.data[0].url, "url 不应为空")
        assertTrue(result.data[0].url!!.startsWith("http"), "url 应以 http 开头")

        println("========== generateImagesAsUrl ==========")
        println("created: ${result.created}")
        println("生成图片数量: ${result.data.size}")
        result.data.forEachIndexed { index, imageData ->
            println("图片 #$index:")
            println("  url: ${imageData.url}")
            println("  revisedPrompt: ${imageData.revisedPrompt ?: "(无)"}")
        }
    }

    // ========== generateImagesAsBase64 ==========

    @Test
    fun `generateImagesAsBase64 should return base64 data`() = runTest {
        val result = api.generateImagesAsBase64(
            apiKey,
            GenerateImagesRequest(model = model, prompt = "一只白色的猫"),
        )

        assertTrue(result.data.isNotEmpty(), "data 不应为空")
        assertNotNull(result.data[0].b64Json, "b64_json 不应为空")
        assertTrue(result.data[0].b64Json!!.isNotEmpty(), "b64_json 内容不应为空")

        println("========== generateImagesAsBase64 ==========")
        println("created: ${result.created}")
        println("生成图片数量: ${result.data.size}")
        result.data.forEachIndexed { index, imageData ->
            println("图片 #$index:")
            println("  base64 长度: ${imageData.b64Json?.length} 字符")
            println("  base64 前100字符: ${imageData.b64Json?.take(100)}...")
            println("  revisedPrompt: ${imageData.revisedPrompt ?: "(无)"}")
        }
    }

    // ========== generateImages ==========

    @Test
    fun `generateImages should return result`() = runTest {
        val result = api.generateImages(
            apiKey,
            GenerateImagesRequest(
                model = model,
                prompt = "一只白色的猫",
                responseFormat = ResponseFormat.URL,
            ),
        )

        assertTrue(result.data.isNotEmpty(), "data 不应为空")
        assertTrue(result.created > 0, "created 时间戳应大于 0")

        println("========== generateImages ==========")
        println("created: ${result.created}")
        println("生成图片数量: ${result.data.size}")
        result.data.forEachIndexed { index, imageData ->
            println("图片 #$index:")
            println("  url: ${imageData.url ?: "(无)"}")
            println("  base64 长度: ${imageData.b64Json?.length ?: 0} 字符")
            println("  revisedPrompt: ${imageData.revisedPrompt ?: "(无)"}")
        }
    }

    // ========== streamGenerateImages ==========

    @Test
    fun `streamGenerateImages should return Flow of events`() = runTest {
        val events = api.streamGenerateImages(
            apiKey,
            GenerateImagesRequest(model = model, prompt = "一只胖胖的橘猫跳起来抓飞在空中的毛线球"),
        ).toList()

        assertTrue(events.isNotEmpty(), "events 不应为空")

        println("========== streamGenerateImages ==========")
        println("共 ${events.size} 个事件")
        events.forEachIndexed { index, event ->
            println("事件 #$index:")
            println("  type: ${event.type}")
            println("  created: ${event.created}")
            if (event.data != null) {
                println("  url: ${event.data.url ?: "(无)"}")
                println("  base64 长度: ${event.data.b64Json?.length ?: 0} 字符")
                println("  revisedPrompt: ${event.data.revisedPrompt ?: "(无)"}")
            }
        }
    }
}
