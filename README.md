# Volcengine Ark SDK for Kotlin

[English](README_EN.md) | 简体中文

火山引擎方舟大模型平台的 Kotlin SDK，提供简洁易用的 API 接口，支持文本生成、向量化、图像生成等功能。

[![](https://jitpack.io/v/xingray/volcengine-ark-sdk.svg)](https://jitpack.io/#xingray/volcengine-ark-sdk)

## 特性

- 🚀 **完整的 API 支持** - 覆盖火山方舟平台所有主要功能
- 🔄 **流式响应** - 支持 SSE 流式输出，实时获取模型响应
- 🎯 **类型安全** - 使用 Kotlin 类型系统和 kotlinx.serialization
- ⚡ **协程支持** - 基于 Kotlin 协程的异步 API
- 🛠️ **易于使用** - 简洁的 API 设计，开箱即用

## 支持的功能

### 文本生成 (Chat Completion)
- ✅ 基础对话生成（流式/非流式）
- ✅ 深度思考模式 (Thinking)
- ✅ 续写模式 (Prefill)
- ✅ 函数调用 (Function Calling)
- ✅ 结构化输出 (JSON Schema)

### Bot 对话
- ✅ 预配置 Bot 对话（流式/非流式）
- ✅ 知识库检索
- ✅ 插件调用

### 文本向量化 (Embedding)
- ✅ 文本向量化
- ✅ 批量文本向量化
- ✅ 多模态向量化（文本+图片）
- ✅ 批量多模态向量化

### 图像生成
- ✅ 文生图 (Text-to-Image)
- ✅ URL 格式输出
- ✅ Base64 格式输出
- ✅ 流式图像生成

### 内容生成
- ✅ 创建内容生成任务
- ✅ 查询任务状态
- ✅ 轮询等待任务完成
- ✅ 列出任务
- ✅ 删除任务

### Responses API
- ✅ 创建 Response（流式/非流式）
- ✅ 查询 Response
- ✅ 删除 Response
- ✅ 列出输入项

### 其他功能
- ✅ Token 计数 (Tokenization)
- ✅ 文件管理 (File API)
- ✅ 上下文管理 (Context API)

## 安装

### Gradle (Kotlin DSL)

首先在项目根目录的 `settings.gradle.kts` 中添加 JitPack 仓库：

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

然后在模块的 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation("com.github.xingray:volcengine-ark-sdk:0.0.1")
}
```

### Gradle (Groovy)

在项目根目录的 `settings.gradle` 中添加 JitPack 仓库：

```groovy
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

然后在模块的 `build.gradle` 中添加依赖：

```groovy
dependencies {
    implementation 'com.github.xingray:volcengine-ark-sdk:0.0.1'
}
```

### Maven

在 `pom.xml` 中添加 JitPack 仓库：

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

然后添加依赖：

```xml
<dependency>
    <groupId>com.github.xingray</groupId>
    <artifactId>volcengine-ark-sdk</artifactId>
    <version>0.0.1</version>
</dependency>
```

## 快速开始

### 1. 创建客户端

```kotlin
import com.github.xingray.volcenginesdk.ArkClient

val client = ArkClient()
val apiKey = "your-api-key"
```

### 2. 文本生成

```kotlin
import com.github.xingray.volcenginesdk.api.ChatCompletionApi
import com.github.xingray.volcenginesdk.model.chat.*
import kotlinx.serialization.json.JsonPrimitive

val api = ChatCompletionApi(client)

// 非流式
val result = api.createChatCompletion(
    apiKey = apiKey,
    request = ChatCompletionRequest(
        model = "doubao-seed-1.6-250615",
        messages = listOf(
            ChatMessage(
                role = ChatMessageRole.USER,
                content = JsonPrimitive("你好，请介绍一下你自己")
            )
        ),
        maxTokens = 1000
    )
)

println(result.choices[0].message?.content)
```

### 3. 流式文本生成

```kotlin
import kotlinx.coroutines.flow.collect

// 流式
api.streamChatCompletion(
    apiKey = apiKey,
    request = ChatCompletionRequest(
        model = "doubao-seed-1.6-250615",
        messages = listOf(
            ChatMessage(
                role = ChatMessageRole.USER,
                content = JsonPrimitive("讲一个故事")
            )
        ),
        maxTokens = 1000
    )
).collect { chunk ->
    chunk.choices.firstOrNull()?.delta?.content?.let { content ->
        print(content)
    }
}
```

### 4. 深度思考模式

```kotlin
val result = api.createChatCompletionWithThinking(
    apiKey = apiKey,
    model = "doubao-seed-1.6-250615",
    messages = listOf(
        ChatMessage(
            role = ChatMessageRole.USER,
            content = JsonPrimitive("解释一下量子纠缠")
        )
    ),
    thinkingType = "enabled",
    maxTokens = 2000
)

// 查看推理过程
println("推理过程: ${result.choices[0].message?.reasoningContent}")
// 查看最终回复
println("最终回复: ${result.choices[0].message?.content}")
```

### 5. 函数调用

```kotlin
import kotlinx.serialization.json.*

val tools = listOf(
    ChatTool(
        type = "function",
        function = ChatFunction(
            name = "get_weather",
            description = "获取指定城市的天气信息",
            parameters = buildJsonObject {
                put("type", JsonPrimitive("object"))
                put("properties", buildJsonObject {
                    put("city", buildJsonObject {
                        put("type", JsonPrimitive("string"))
                        put("description", JsonPrimitive("城市名称"))
                    })
                })
                put("required", buildJsonArray {
                    add(JsonPrimitive("city"))
                })
            }
        )
    )
)

val result = api.createChatCompletionWithTools(
    apiKey = apiKey,
    model = "doubao-seed-1.6-250615",
    messages = listOf(
        ChatMessage(
            role = ChatMessageRole.USER,
            content = JsonPrimitive("北京今天天气怎么样？")
        )
    ),
    tools = tools,
    maxTokens = 1000
)

// 检查是否有函数调用
result.choices[0].message?.toolCalls?.forEach { toolCall ->
    println("函数名: ${toolCall.function?.name}")
    println("参数: ${toolCall.function?.arguments}")
}
```

### 6. 结构化输出

```kotlin
val jsonSchema = JsonSchemaParam(
    name = "user_info",
    description = "用户信息",
    schema = buildJsonObject {
        put("type", JsonPrimitive("object"))
        put("properties", buildJsonObject {
            put("name", buildJsonObject {
                put("type", JsonPrimitive("string"))
                put("description", JsonPrimitive("姓名"))
            })
            put("age", buildJsonObject {
                put("type", JsonPrimitive("integer"))
                put("description", JsonPrimitive("年龄"))
            })
        })
        put("required", buildJsonArray {
            add(JsonPrimitive("name"))
            add(JsonPrimitive("age"))
        })
    },
    strict = true
)

val result = api.createChatCompletionWithJsonOutput(
    apiKey = apiKey,
    model = "doubao-seed-1.6-250615",
    messages = listOf(
        ChatMessage(
            role = ChatMessageRole.USER,
            content = JsonPrimitive("我叫张三，今年25岁")
        )
    ),
    jsonSchema = jsonSchema,
    maxTokens = 500
)

// 输出将是符合 JSON Schema 的结构化数据
println(result.choices[0].message?.content)
```

### 7. 文本向量化

```kotlin
import com.github.xingray.volcenginesdk.api.EmbeddingApi
import com.github.xingray.volcenginesdk.model.embedding.EmbeddingRequest

val embeddingApi = EmbeddingApi(client)

val result = embeddingApi.createEmbeddings(
    apiKey = apiKey,
    request = EmbeddingRequest(
        model = "doubao-embedding",
        input = listOf("你好，世界", "Hello, World")
    )
)

result.data.forEach { embedding ->
    println("向量维度: ${embedding.embedding.size}")
    println("向量前5维: ${embedding.embedding.take(5)}")
}
```

### 8. 图像生成

```kotlin
import com.github.xingray.volcenginesdk.api.ImageGenerationApi
import com.github.xingray.volcenginesdk.model.image.GenerateImagesRequest

val imageApi = ImageGenerationApi(client)

// 方式1: 生成图片并返回 URL
val urlResult = imageApi.generateImagesAsUrl(
    apiKey = apiKey,
    request = GenerateImagesRequest(
        model = "doubao-seedream-5-0-260128",
        prompt = "一只可爱的猫咪在花园里玩耍",
        n = 1,
        size = "1024x1024"
    )
)

urlResult.data.forEach { image ->
    println("图片 URL: ${image.url}")
}

// 方式2: 生成图片并返回 Base64 编码
val base64Result = imageApi.generateImagesAsBase64(
    apiKey = apiKey,
    request = GenerateImagesRequest(
        model = "doubao-seedream-5-0-260128",
        prompt = "一只可爱的猫咪在花园里玩耍",
        n = 1,
        size = "1024x1024"
    )
)

base64Result.data.forEach { image ->
    println("图片 Base64: ${image.b64Json?.take(50)}...")
}

// 方式3: 流式生成图片（实时获取生成进度）
imageApi.streamGenerateImages(
    apiKey = apiKey,
    request = GenerateImagesRequest(
        model = "doubao-seedream-5-0-260128",
        prompt = "一只可爱的猫咪在花园里玩耍",
        n = 1,
        size = "1024x1024"
    )
).collect { event ->
    println("进度: ${event.progress}%")
    if (event.data != null) {
        println("图片生成完成: ${event.data.url}")
    }
}
```

### 9. Bot 对话

```kotlin
import com.github.xingray.volcenginesdk.api.BotChatApi
import com.github.xingray.volcenginesdk.model.bot.BotChatCompletionRequest

val botApi = BotChatApi(client)

val result = botApi.createBotChatCompletion(
    apiKey = apiKey,
    request = BotChatCompletionRequest(
        model = "your-bot-id",
        messages = listOf(
            ChatMessage(
                role = ChatMessageRole.USER,
                content = JsonPrimitive("你好")
            )
        )
    )
)

println(result.choices[0].message?.content)

// 查看引用来源
result.references?.forEach { ref ->
    println("引用: ${ref.title} - ${ref.url}")
}
```

## 配置

### 自定义基础 URL

```kotlin
val client = ArkClient(
    baseUrl = "https://your-custom-endpoint.com/api/v3"
)
```

### 自定义 HTTP 客户端

```kotlin
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import kotlin.time.Duration.Companion.minutes

val httpClient = ArkClient.createDefaultHttpClient(
    timeout = 5.minutes,
    connectTimeout = 30.seconds,
    enableLogging = true
)

val client = ArkClient(httpClient = httpClient)
```

### 自定义 JSON 配置

```kotlin
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
}

val client = ArkClient(json = json)
```

## 常量定义

SDK 提供了 `ArkConstants` 对象来管理所有常量：

```kotlin
import com.github.xingray.volcenginesdk.ArkConstants

// API 端点
ArkConstants.Endpoint.CHAT_COMPLETIONS
ArkConstants.Endpoint.EMBEDDINGS
ArkConstants.Endpoint.IMAGE_GENERATIONS

// 模型 ID
ArkConstants.ModelId.SEEDREAM_5_0_LITE
ArkConstants.ModelId.SEEDREAM_4_5

// 工具类型
ArkConstants.Tools.WEB_SEARCH

// 响应格式
ArkConstants.ResponseFormatType.JSON_OBJECT
ArkConstants.ResponseFormatType.JSON_SCHEMA
```

## 错误处理

```kotlin
import com.github.xingray.volcenginesdk.ArkException

try {
    val result = api.createChatCompletion(apiKey, request)
    // 处理结果
} catch (e: ArkException) {
    println("API 错误: ${e.message}")
    println("错误码: ${e.errorResponse?.error?.code}")
    println("错误信息: ${e.errorResponse?.error?.message}")
}
```

## 测试

运行测试需要设置环境变量 `ARK_API_KEY`：

```bash
export ARK_API_KEY=your-api-key
./gradlew test
```

如果未设置 API Key，测试将被跳过而不是失败。

## 依赖

- Kotlin 2.3.10+
- Ktor 3.4.1
- kotlinx.serialization 1.10.0
- kotlinx.coroutines 1.10.2
- JDK 17+

## 许可证

本项目采用 [Apache License 2.0](LICENSE) 许可证。

## 贡献

欢迎提交 Issue 和 Pull Request！

## 相关链接

- [火山引擎方舟平台](https://www.volcengine.com/product/ark)
- [API 文档](https://www.volcengine.com/docs/82379)
- [GitHub 仓库](https://github.com/xingray/volcengine-ark-sdk)
- [JitPack](https://jitpack.io/#xingray/volcengine-ark-sdk)

## 更新日志

### 0.0.1 (2026-03-06)

- 🎉 首次发布
- ✅ 支持所有主要 API 功能
- ✅ 完整的类型定义
- ✅ 流式响应支持
- ✅ 协程支持
- ✅ 图像生成支持 URL 和 Base64 两种格式
