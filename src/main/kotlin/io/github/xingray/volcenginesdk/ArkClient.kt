package io.github.xingray.volcenginesdk

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.io.Closeable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

/**
 * 火山方舟 Ark 平台 API 客户端。
 *
 * 提供与火山引擎方舟大模型平台通信的基础能力，包括 HTTP 请求发送、JSON 序列化配置等。
 * apiKey 不在此处绑定，而是在每个 API 调用时传入，以支持 apiKey 池的场景。
 *
 * @param baseUrl API 基础地址，默认为火山方舟北京区域地址
 * @param httpClient Ktor HttpClient 实例，可由外部自定义传入，默认使用 OkHttp 引擎
 * @param json kotlinx.serialization 的 Json 实例，可由外部自定义传入
 */
class ArkClient(
    val baseUrl: String = ArkConstants.BaseUrl.DEFAULT,
    val json: Json = createDefaultJson(),
    val httpClient: HttpClient = createDefaultHttpClient(json = json),
) : Closeable {

    companion object {

        /**
         * 创建默认的 HttpClient，使用 OkHttp 引擎。
         *
         * @param timeout 请求超时时间，默认 10 分钟
         * @param connectTimeout 连接超时时间，默认 1 分钟
         * @param enableLogging 是否启用日志，默认关闭
         * @param json 用于 ContentNegotiation 的 Json 实例
         * @return 配置好的 HttpClient 实例
         */
        fun createDefaultHttpClient(
            timeout: Duration = 10.minutes,
            connectTimeout: Duration = 1.minutes,
            enableLogging: Boolean = false,
            json: Json = createDefaultJson(),
        ): HttpClient {
            return HttpClient(OkHttp) {
                install(ContentNegotiation) {
                    json(json)
                }

                if (enableLogging) {
                    install(Logging) {
                        logger = Logger.DEFAULT
                        level = LogLevel.BODY
                    }
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = timeout.inWholeMilliseconds
                    connectTimeoutMillis = connectTimeout.inWholeMilliseconds
                    socketTimeoutMillis = timeout.inWholeMilliseconds
                }
            }
        }

        /**
         * 创建默认的 Json 序列化配置。
         *
         * @return 配置好的 Json 实例
         */
        fun createDefaultJson(): Json {
            return Json {
                ignoreUnknownKeys = true
                encodeDefaults = false
                isLenient = true
                explicitNulls = false
            }
        }
    }

    /**
     * 发送 POST 请求。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param path 请求路径（相对于 baseUrl）
     * @param body 请求体
     * @return HTTP 响应
     */
    internal suspend fun post(apiKey: String, path: String, body: Any): HttpResponse {
        return httpClient.post("$baseUrl$path") {
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    /**
     * 发送 POST 请求（用于流式响应，设置 Accept: text/event-stream）。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param path 请求路径（相对于 baseUrl）
     * @param body 请求体
     * @return HTTP 响应
     */
    internal suspend fun postStream(apiKey: String, path: String, body: Any): HttpResponse {
        return httpClient.post("$baseUrl$path") {
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Accept, "text/event-stream")
            setBody(body)
        }
    }

    /**
     * 发送 GET 请求。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param path 请求路径（相对于 baseUrl）
     * @param block 可选的请求构建器配置
     * @return HTTP 响应
     */
    internal suspend fun get(
        apiKey: String,
        path: String,
        block: HttpRequestBuilder.() -> Unit = {},
    ): HttpResponse {
        return httpClient.get("$baseUrl$path") {
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            contentType(ContentType.Application.Json)
            block()
        }
    }

    /**
     * 发送 DELETE 请求。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param path 请求路径（相对于 baseUrl）
     * @return HTTP 响应
     */
    internal suspend fun delete(apiKey: String, path: String): HttpResponse {
        return httpClient.delete("$baseUrl$path") {
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            contentType(ContentType.Application.Json)
        }
    }

    /**
     * 关闭底层 HttpClient。
     */
    override fun close() {
        httpClient.close()
    }
}
