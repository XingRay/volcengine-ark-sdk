package io.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tool(

    @SerialName("type")
    val type: String,
)
