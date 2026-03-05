package io.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SequentialImageGeneration {
    @SerialName("auto")
    AUTO,

    @SerialName("disabled")
    DISABLED,

}