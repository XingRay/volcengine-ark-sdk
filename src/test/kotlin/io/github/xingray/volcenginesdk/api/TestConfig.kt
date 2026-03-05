package io.github.xingray.volcenginesdk.api

import io.github.xingray.volcenginesdk.ArkClient
import org.junit.jupiter.api.Assumptions

/**
 * 测试配置。
 *
 * API Key 通过环境变量 ARK_API_KEY 读取，未设置时跳过测试。
 */
object TestConfig {

    /** API Key，从环境变量 ARK_API_KEY 读取，可能为 null */
    val apiKey: String? = System.getenv("ARK_API_KEY")

    init {
        if (apiKey == null) {
            System.err.println("WARNING: Environment variable ARK_API_KEY is not set, all API tests will be skipped")
        }
    }

    /** 检查 API Key 是否已设置，未设置时跳过测试 */
    fun requireApiKey(): String {
        Assumptions.assumeTrue(apiKey != null, "Environment variable ARK_API_KEY is not set, skipping test")
        return apiKey!!
    }

    /** 创建 ArkClient 实例 */
    fun createClient(): ArkClient = ArkClient()
}
