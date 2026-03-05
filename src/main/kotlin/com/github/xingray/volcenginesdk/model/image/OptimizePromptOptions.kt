package com.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OptimizePromptOptions(

    @SerialName("mode")
    val mode: Mode,
)
