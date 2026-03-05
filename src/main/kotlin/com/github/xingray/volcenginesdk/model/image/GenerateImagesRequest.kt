package com.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 图片生成请求参数。
 *
 * @property model 模型 ID，如 "doubao-seedream-3-0"
 * @property prompt 图片描述文本
 * @property image 参考图片列表（用于图片编辑）
 * @property responseFormat 返回格式，"url" 或 "b64_json"
 * @property seed 随机种子
 * @property guidanceScale 引导系数
 * @property size 图片尺寸，如 "1024x1024"
 * @property watermark 是否添加水印
 * @property optimizePrompt 是否优化提示词
 * @property sequentialImageGeneration 顺序生成模式
 * @property stream 是否启用流式输出
 * @property n 生成图片数量
 */
@Serializable
data class GenerateImagesRequest(
    @SerialName("model")
    val model: String,

    @SerialName("prompt")
    val prompt: String,

    @SerialName("image")
    val image: List<String>? = null,

    @SerialName("size")
    val size: String? = null,

    @SerialName("seed")
    val seed: Int? = null,

    @SerialName("sequential_image_generation")
    val sequentialImageGeneration: String? = null,

    @SerialName("sequential_image_generation_options")
    val sequentialImageGenerationOptions: SequentialImageGenerationOptions? = null,

    @SerialName("tools")
    val tools: List<Tool>? = null,

    @SerialName("stream")
    val stream: Boolean? = null,

    @SerialName("guidance_scale")
    val guidanceScale: Double? = null,

    @SerialName("output_format")
    val outputFormat: String? = null,

    @SerialName("response_format")
    val responseFormat: ResponseFormat? = null,

    @SerialName("watermark")
    val watermark: Boolean? = null,

    @SerialName("optimize_prompt_options")
    val optimizePromptOptions: OptimizePromptOptions? = null,

    )
