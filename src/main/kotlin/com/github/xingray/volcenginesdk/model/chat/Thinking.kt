package com.github.xingray.volcenginesdk.model.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 深度思考配置。
 *
 * 启用后模型会先进行推理分析再生成最终回复。
 *
 * @property type 思考模式类型，可选 "enabled" 或 "disabled"
 */
@Serializable
data class Thinking(
    @SerialName("type")
    val type: String,
)
