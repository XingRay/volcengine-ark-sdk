package io.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Mode {

    @SerialName("standard")
    STANDARD,

    @SerialName("fast")
    FAST,

}