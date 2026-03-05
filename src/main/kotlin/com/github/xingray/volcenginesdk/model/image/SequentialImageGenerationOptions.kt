package com.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SequentialImageGenerationOptions(
    @SerialName("max_images")
    val maxImages: Int? = null,

    )