package io.github.xingray.volcenginesdk.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ResponseFormat {

    @SerialName("url")
    URL,

    @SerialName("b64_json")
    BASE64_JSON,

}