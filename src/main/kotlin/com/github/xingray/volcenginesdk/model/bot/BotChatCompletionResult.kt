package com.github.xingray.volcenginesdk.model.bot

import com.github.xingray.volcenginesdk.model.Usage
import com.github.xingray.volcenginesdk.model.chat.ChatCompletionChoice
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 豆包助手对话响应结果。
 *
 * @property id 本次请求的唯一标识
 * @property objectType 对象类型
 * @property created 创建时间戳（秒）
 * @property model 使用的模型标识
 * @property choices 生成结果列表
 * @property usage token 使用统计
 * @property references 引用来源列表
 */
@Serializable
data class BotChatCompletionResult(
    @SerialName("id")
    val id: String? = null,

    @SerialName("object")
    val objectType: String? = null,

    @SerialName("created")
    val created: Long = 0,

    @SerialName("model")
    val model: String? = null,

    @SerialName("choices")
    val choices: List<ChatCompletionChoice> = emptyList(),

    @SerialName("usage")
    val usage: Usage? = null,

    @SerialName("references")
    val references: List<BotReference>? = null,
)
