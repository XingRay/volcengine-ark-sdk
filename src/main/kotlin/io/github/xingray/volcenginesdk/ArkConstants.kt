package io.github.xingray.volcenginesdk

/**
 * 火山方舟 SDK 常量定义。
 *
 * 包含 API 基础地址、端点路径、HTTP 头等项目中使用的字面量常量。
 */
object ArkConstants {

    /**
     * API 基础地址
     */
    object BaseUrl {
        /** 默认 API 基础地址（北京区域） */
        const val DEFAULT = "https://ark.cn-beijing.volces.com/api/v3"
    }

    /**
     * API 端点路径
     */
    object Endpoint {
        /** 文本生成（Chat Completion） */
        const val CHAT_COMPLETIONS = "/chat/completions"

        /** Bot 对话 */
        const val BOT_CHAT_COMPLETIONS = "/bots/chat/completions"

        /** 文本向量化（Embedding） */
        const val EMBEDDINGS = "/embeddings"

        /** 批量文本向量化 */
        const val BATCH_EMBEDDINGS = "/batch/embeddings"

        /** 多模态向量化 */
        const val MULTIMODAL_EMBEDDINGS = "/embeddings/multimodal"

        /** 批量多模态向量化 */
        const val BATCH_MULTIMODAL_EMBEDDINGS = "/batch/embeddings/multimodal"

        /** 图像生成 */
        const val IMAGE_GENERATIONS = "/images/generations"

        /** 内容生成任务 */
        const val CONTENT_GENERATION_TASKS = "/contents/generations/tasks"

        /** Token 计数 */
        const val TOKENIZATION = "/tokenization"

        /** 文件管理 */
        const val FILES = "/files"

        /** 上下文创建 */
        const val CONTEXT_CREATE = "/context/create"

        /** Responses API */
        const val RESPONSES = "/responses"
    }

    /**
     * HTTP 头常量
     */
    object Header {
        /** 授权头前缀 */
        const val AUTHORIZATION_PREFIX = "Bearer"

        /** SSE 内容类型 */
        const val CONTENT_TYPE_SSE = "text/event-stream"
    }

    /**
     * 默认配置值
     */
    object Default {
        /** 默认请求超时时间（毫秒） */
        const val REQUEST_TIMEOUT_MILLIS = 600_000L // 10 分钟

        /** 默认连接超时时间（毫秒） */
        const val CONNECT_TIMEOUT_MILLIS = 60_000L // 1 分钟

        /** 默认 Socket 超时时间（毫秒） */
        const val SOCKET_TIMEOUT_MILLIS = 600_000L // 10 分钟
    }

    /**
     * 思考模式类型
     */
    object ThinkingType {
        /** 启用思考模式 */
        const val ENABLED = "enabled"

        /** 禁用思考模式 */
        const val DISABLED = "disabled"
    }

    /**
     * 响应格式类型
     */
    object ResponseFormatType {
        /** JSON 对象格式 */
        const val JSON_OBJECT = "json_object"

        /** JSON Schema 格式 */
        const val JSON_SCHEMA = "json_schema"

        /** 文本格式 */
        const val TEXT = "text"
    }

    /**
     * SSE 事件类型
     */
    object SseEvent {
        /** 数据事件前缀 */
        const val DATA_PREFIX = "data:"

        /** 流结束标记 */
        const val DONE_MARKER = "[DONE]"
    }

    object ModelId {
        const val SEEDREAM_5_0_LITE = "doubao-seedream-5-0-260128"
        const val SEEDREAM_4_5 = "doubao-seedream-4-5-251128"
        const val SEEDREAM_4_0 = "doubao-seedream-4-0-250828"
        const val SEEDREAM_3_0_T2I = "doubao-seedream-3-0-t2i-250415"
        const val SEEDEDIT_3_0_I2I = "doubao-seededit-3-0-i2i-250628"

    }

    object Tools{
        const val WEB_SEARCH = "web_search"
    }
}
