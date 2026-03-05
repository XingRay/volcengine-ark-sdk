# Volcengine Ark SDK for Kotlin

English | [简体中文](README.md)

A Kotlin SDK for Volcengine Ark AI Platform, providing clean and easy-to-use APIs for text generation, embeddings, image generation, and more.

[![](https://jitpack.io/v/xingray/volcengine-ark-sdk.svg)](https://jitpack.io/#xingray/volcengine-ark-sdk)

## Features

- 🚀 **Complete API Coverage** - Supports all major features of Volcengine Ark Platform
- 🔄 **Streaming Support** - SSE streaming for real-time model responses
- 🎯 **Type Safety** - Leverages Kotlin type system and kotlinx.serialization
- ⚡ **Coroutines** - Async APIs based on Kotlin coroutines
- 🛠️ **Easy to Use** - Clean API design, ready to use out of the box

## Supported Features

### Text Generation (Chat Completion)
- ✅ Basic chat completion (streaming/non-streaming)
- ✅ Thinking mode (deep reasoning)
- ✅ Prefill mode (continuation)
- ✅ Function calling
- ✅ Structured output (JSON Schema)

### Bot Chat
- ✅ Pre-configured bot chat (streaming/non-streaming)
- ✅ Knowledge base retrieval
- ✅ Plugin invocation

### Text Embeddings
- ✅ Text embeddings
- ✅ Batch text embeddings
- ✅ Multimodal embeddings (text + image)
- ✅ Batch multimodal embeddings

### Image Generation
- ✅ Text-to-Image
- ✅ URL format output
- ✅ Base64 format output
- ✅ Streaming image generation

### Content Generation
- ✅ Create content generation tasks
- ✅ Query task status
- ✅ Poll and wait for task completion
- ✅ List tasks
- ✅ Delete tasks

### Responses API
- ✅ Create response (streaming/non-streaming)
- ✅ Get response
- ✅ Delete response
- ✅ List input items

### Other Features
- ✅ Token counting (Tokenization)
- ✅ File management (File API)
- ✅ Context management (Context API)

## Installation

### Gradle (Kotlin DSL)

First, add JitPack repository in your root `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Then add the dependency in your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.xingray:volcengine-ark-sdk:0.0.1")
}
```

### Gradle (Groovy)

Add JitPack repository in your root `settings.gradle`:

```groovy
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Then add the dependency in your module's `build.gradle`:

```groovy
dependencies {
    implementation 'com.github.xingray:volcengine-ark-sdk:0.0.1'
}
```

### Maven

Add JitPack repository in your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then add the dependency:

```xml
<dependency>
    <groupId>com.github.xingray</groupId>
    <artifactId>volcengine-ark-sdk</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Quick Start

### 1. Create Client

```kotlin
import io.github.xingray.volcenginesdk.ArkClient

val client = ArkClient()
val apiKey = "your-api-key"
```

### 2. Text Generation

```kotlin
import io.github.xingray.volcenginesdk.api.ChatCompletionApi
import io.github.xingray.volcenginesdk.model.chat.*
import kotlinx.serialization.json.JsonPrimitive

val api = ChatCompletionApi(client)

// Non-streaming
val result = api.createChatCompletion(
    apiKey = apiKey,
    request = ChatCompletionRequest(
        model = "doubao-seed-1.6-250615",
        messages = listOf(
            ChatMessage(
                role = ChatMessageRole.USER,
                content = JsonPrimitive("Hello, please introduce yourself")
            )
        ),
        maxTokens = 1000
    )
)

println(result.choices[0].message?.content)
```

### 3. Streaming Text Generation

```kotlin
import kotlinx.coroutines.flow.collect

// Streaming
api.streamChatCompletion(
    apiKey = apiKey,
    request = ChatCompletionRequest(
        model = "doubao-seed-1.6-250615",
        messages = listOf(
            ChatMessage(
                role = ChatMessageRole.USER,
                content = JsonPrimitive("Tell me a story")
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

### 4. Thinking Mode

```kotlin
val result = api.createChatCompletionWithThinking(
    apiKey = apiKey,
    model = "doubao-seed-1.6-250615",
    messages = listOf(
        ChatMessage(
            role = ChatMessageRole.USER,
            content = JsonPrimitive("Explain quantum entanglement")
        )
    ),
    thinkingType = "enabled",
    maxTokens = 2000
)

// View reasoning process
println("Reasoning: ${result.choices[0].message?.reasoningContent}")
// View final response
println("Response: ${result.choices[0].message?.content}")
```

### 5. Function Calling

```kotlin
import kotlinx.serialization.json.*

val tools = listOf(
    ChatTool(
        type = "function",
        function = ChatFunction(
            name = "get_weather",
            description = "Get weather information for a city",
            parameters = buildJsonObject {
                put("type", JsonPrimitive("object"))
                put("properties", buildJsonObject {
                    put("city", buildJsonObject {
                        put("type", JsonPrimitive("string"))
                        put("description", JsonPrimitive("City name"))
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
            content = JsonPrimitive("What's the weather like in Beijing today?")
        )
    ),
    tools = tools,
    maxTokens = 1000
)

// Check for function calls
result.choices[0].message?.toolCalls?.forEach { toolCall ->
    println("Function: ${toolCall.function?.name}")
    println("Arguments: ${toolCall.function?.arguments}")
}
```

### 6. Structured Output

```kotlin
val jsonSchema = JsonSchemaParam(
    name = "user_info",
    description = "User information",
    schema = buildJsonObject {
        put("type", JsonPrimitive("object"))
        put("properties", buildJsonObject {
            put("name", buildJsonObject {
                put("type", JsonPrimitive("string"))
                put("description", JsonPrimitive("Name"))
            })
            put("age", buildJsonObject {
                put("type", JsonPrimitive("integer"))
                put("description", JsonPrimitive("Age"))
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
            content = JsonPrimitive("My name is John and I'm 25 years old")
        )
    ),
    jsonSchema = jsonSchema,
    maxTokens = 500
)

// Output will be structured data conforming to JSON Schema
println(result.choices[0].message?.content)
```

### 7. Text Embeddings

```kotlin
import io.github.xingray.volcenginesdk.api.EmbeddingApi
import io.github.xingray.volcenginesdk.model.embedding.EmbeddingRequest

val embeddingApi = EmbeddingApi(client)

val result = embeddingApi.createEmbeddings(
    apiKey = apiKey,
    request = EmbeddingRequest(
        model = "doubao-embedding",
        input = listOf("Hello, World", "你好，世界")
    )
)

result.data.forEach { embedding ->
    println("Dimensions: ${embedding.embedding.size}")
    println("First 5 values: ${embedding.embedding.take(5)}")
}
```

### 8. Image Generation

```kotlin
import io.github.xingray.volcenginesdk.api.ImageGenerationApi
import io.github.xingray.volcenginesdk.model.image.GenerateImagesRequest

val imageApi = ImageGenerationApi(client)

// Method 1: Generate image and return URL
val urlResult = imageApi.generateImagesAsUrl(
    apiKey = apiKey,
    request = GenerateImagesRequest(
        model = "doubao-seedream-5-0-260128",
        prompt = "A cute cat playing in a garden",
        n = 1,
        size = "1024x1024"
    )
)

urlResult.data.forEach { image ->
    println("Image URL: ${image.url}")
}

// Method 2: Generate image and return Base64 encoding
val base64Result = imageApi.generateImagesAsBase64(
    apiKey = apiKey,
    request = GenerateImagesRequest(
        model = "doubao-seedream-5-0-260128",
        prompt = "A cute cat playing in a garden",
        n = 1,
        size = "1024x1024"
    )
)

base64Result.data.forEach { image ->
    println("Image Base64: ${image.b64Json?.take(50)}...")
}

// Method 3: Stream image generation (real-time progress)
imageApi.streamGenerateImages(
    apiKey = apiKey,
    request = GenerateImagesRequest(
        model = "doubao-seedream-5-0-260128",
        prompt = "A cute cat playing in a garden",
        n = 1,
        size = "1024x1024"
    )
).collect { event ->
    println("Progress: ${event.progress}%")
    if (event.data != null) {
        println("Image generated: ${event.data.url}")
    }
}
```

### 9. Bot Chat

```kotlin
import io.github.xingray.volcenginesdk.api.BotChatApi
import io.github.xingray.volcenginesdk.model.bot.BotChatCompletionRequest

val botApi = BotChatApi(client)

val result = botApi.createBotChatCompletion(
    apiKey = apiKey,
    request = BotChatCompletionRequest(
        model = "your-bot-id",
        messages = listOf(
            ChatMessage(
                role = ChatMessageRole.USER,
                content = JsonPrimitive("Hello")
            )
        )
    )
)

println(result.choices[0].message?.content)

// View references
result.references?.forEach { ref ->
    println("Reference: ${ref.title} - ${ref.url}")
}
```

## Configuration

### Custom Base URL

```kotlin
val client = ArkClient(
    baseUrl = "https://your-custom-endpoint.com/api/v3"
)
```

### Custom HTTP Client

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

### Custom JSON Configuration

```kotlin
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
}

val client = ArkClient(json = json)
```

## Constants

The SDK provides `ArkConstants` object for managing all constants:

```kotlin
import io.github.xingray.volcenginesdk.ArkConstants

// API Endpoints
ArkConstants.Endpoint.CHAT_COMPLETIONS
ArkConstants.Endpoint.EMBEDDINGS
ArkConstants.Endpoint.IMAGE_GENERATIONS

// Model IDs
ArkConstants.ModelId.SEEDREAM_5_0_LITE
ArkConstants.ModelId.SEEDREAM_4_5

// Tool Types
ArkConstants.Tools.WEB_SEARCH

// Response Format Types
ArkConstants.ResponseFormatType.JSON_OBJECT
ArkConstants.ResponseFormatType.JSON_SCHEMA
```

## Error Handling

```kotlin
import io.github.xingray.volcenginesdk.ArkException

try {
    val result = api.createChatCompletion(apiKey, request)
    // Handle result
} catch (e: ArkException) {
    println("API Error: ${e.message}")
    println("Error Code: ${e.errorResponse?.error?.code}")
    println("Error Message: ${e.errorResponse?.error?.message}")
}
```

## Testing

To run tests, set the `ARK_API_KEY` environment variable:

```bash
export ARK_API_KEY=your-api-key
./gradlew test
```

If the API key is not set, tests will be skipped instead of failing.

## Dependencies

- Kotlin 2.3.10+
- Ktor 3.4.1
- kotlinx.serialization 1.10.0
- kotlinx.coroutines 1.10.2
- JDK 17+

## License

This project is licensed under the [Apache License 2.0](LICENSE).

## Contributing

Issues and Pull Requests are welcome!

## Links

- [Volcengine Ark Platform](https://www.volcengine.com/product/ark)
- [API Documentation](https://www.volcengine.com/docs/82379)
- [GitHub Repository](https://github.com/xingray/volcengine-ark-sdk)
- [JitPack](https://jitpack.io/#xingray/volcengine-ark-sdk)

## Changelog

### 0.0.1 (2026-03-06)

- 🎉 Initial release
- ✅ Support for all major API features
- ✅ Complete type definitions
- ✅ Streaming response support
- ✅ Coroutines support
- ✅ Image generation supports both URL and Base64 formats
