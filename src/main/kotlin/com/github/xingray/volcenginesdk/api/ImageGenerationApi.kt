package com.github.xingray.volcenginesdk.api

import com.github.xingray.volcenginesdk.ArkClient
import com.github.xingray.volcenginesdk.ArkConstants
import com.github.xingray.volcenginesdk.model.image.GenerateImagesRequest
import com.github.xingray.volcenginesdk.model.image.ImageGenStreamEvent
import com.github.xingray.volcenginesdk.model.image.ImagesResponse
import com.github.xingray.volcenginesdk.model.image.ResponseFormat
import com.github.xingray.volcenginesdk.util.checkSuccess
import com.github.xingray.volcenginesdk.util.toSseFlow
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 图片生成 API。
 *
 * 支持文生图、图片编辑，可选返回 URL 或 Base64 格式。
 * 对应 Seedream / Seededit 模型的调用。
 *
 * API 端点: POST /images/generations
 *
 * @param client ArkClient 实例
 */
class ImageGenerationApi(private val client: ArkClient) {

    /**
     * 生成图片，返回 URL 格式。
     *
     * 生成完成后，返回的图片可通过 URL 直接访问。URL 有效期有限，建议及时下载保存。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 图片生成请求参数
     * @return 图片生成响应（包含图片 URL）
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun generateImagesAsUrl(apiKey: String, request: GenerateImagesRequest): ImagesResponse {
        val req = request.copy(responseFormat = ResponseFormat.URL, stream = false)
        return generateImages(apiKey, req)
    }

    /**
     * 生成图片，返回 Base64 格式。
     *
     * 生成完成后，图片数据以 Base64 编码字符串形式返回，适用于无需额外下载的场景。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 图片生成请求参数
     * @return 图片生成响应（包含 Base64 编码数据）
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun generateImagesAsBase64(apiKey: String, request: GenerateImagesRequest): ImagesResponse {
        val req = request.copy(responseFormat = ResponseFormat.BASE64_JSON, stream = false)
        return generateImages(apiKey, req)
    }

    /**
     * 生成图片（通用方法）。
     *
     * 根据请求参数中的 responseFormat 和 stream 设置来决定返回格式和传输方式。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 图片生成请求参数
     * @return 图片生成响应
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun generateImages(apiKey: String, request: GenerateImagesRequest): ImagesResponse {
        val response = client.post(apiKey, ArkConstants.Endpoint.IMAGE_GENERATIONS, request)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 流式生成图片。
     *
     * 通过 SSE（Server-Sent Events）协议逐步接收图片生成进度和最终结果，
     * 适用于需要实时展示生成进度的场景。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param request 图片生成请求参数
     * @return 包含流式图片事件的 Flow
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun streamGenerateImages(apiKey: String, request: GenerateImagesRequest): Flow<ImageGenStreamEvent> {
        val req = request.copy(stream = true)
        val response = client.postStream(apiKey, ArkConstants.Endpoint.IMAGE_GENERATIONS, req)
        response.checkSuccess()
        return response.toSseFlow().map { data ->
            client.json.decodeFromString<ImageGenStreamEvent>(data)
        }
    }
}
