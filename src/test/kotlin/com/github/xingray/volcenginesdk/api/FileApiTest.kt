package com.github.xingray.volcenginesdk.api

import kotlinx.coroutines.test.runTest
import java.io.File
import kotlin.test.*

/**
 * FileApi 集成测试。
 *
 * 使用真实 API 进行测试，需要设置环境变量 ARK_API_KEY。
 */
class FileApiTest {

    private val apiKey get() = TestConfig.requireApiKey()
    private val client = TestConfig.createClient()
    private val api = FileApi(client)

    @AfterTest
    fun tearDown() {
        client.close()
    }

    // ========== uploadFile ==========

    @Test
    fun `uploadFile should return file metadata`() = runTest {
        val tempFile = File.createTempFile("volcengine-sdk-test", ".txt")
        tempFile.writeText("这是一个用于测试文件上传的文本文件。\n包含多行内容。\n第三行。")

        try {
            val result = api.uploadFile(apiKey, tempFile)

            assertNotNull(result.id, "文件 id 不应为空")
            assertNotNull(result.filename, "filename 不应为空")
            assertNotNull(result.purpose, "purpose 不应为空")
            assertTrue(result.bytes > 0, "文件大小应大于 0")

            println("========== uploadFile ==========")
            println("id: ${result.id}")
            println("filename: ${result.filename}")
            println("purpose: ${result.purpose}")
            println("bytes: ${result.bytes}")
            println("createdAt: ${result.createdAt}")

            // 清理：删除上传的文件
            api.deleteFile(apiKey, result.id)
        } finally {
            tempFile.delete()
        }
    }

    // ========== retrieveFile ==========

    @Test
    fun `retrieveFile should return file metadata`() = runTest {
        val tempFile = File.createTempFile("volcengine-sdk-test", ".txt")
        tempFile.writeText("测试文件内容")

        try {
            // 先上传一个文件
            val uploadResult = api.uploadFile(apiKey, tempFile)
            assertNotNull(uploadResult.id)

            // 查询文件信息
            val fileInfo = api.retrieveFile(apiKey, uploadResult.id)

            assertEquals(uploadResult.id, fileInfo.id)
            assertNotNull(fileInfo.filename, "filename 不应为空")
            assertTrue(fileInfo.bytes > 0, "文件大小应大于 0")

            println("========== retrieveFile ==========")
            println("id: ${fileInfo.id}")
            println("filename: ${fileInfo.filename}")
            println("purpose: ${fileInfo.purpose}")
            println("bytes: ${fileInfo.bytes}")
            println("createdAt: ${fileInfo.createdAt}")

            // 清理
            api.deleteFile(apiKey, uploadResult.id)
        } finally {
            tempFile.delete()
        }
    }

    // ========== listFiles ==========

    @Test
    fun `listFiles should return file list`() = runTest {
        val result = api.listFiles(apiKey)

        assertNotNull(result.data, "data 不应为空")

        println("========== listFiles ==========")
        println("共 ${result.data.size} 个文件")
        result.data.forEachIndexed { index, file ->
            println("文件 #$index:")
            println("  id: ${file.id}")
            println("  filename: ${file.filename}")
            println("  purpose: ${file.purpose}")
            println("  bytes: ${file.bytes}")
            println("  createdAt: ${file.createdAt}")
        }
    }

    // ========== deleteFile ==========

    @Test
    fun `deleteFile should delete file successfully`() = runTest {
        val tempFile = File.createTempFile("volcengine-sdk-test-delete", ".txt")
        tempFile.writeText("待删除的测试文件")

        try {
            // 先上传一个文件
            val uploadResult = api.uploadFile(apiKey, tempFile)
            assertNotNull(uploadResult.id)

            // 删除文件
            val deleteResult = api.deleteFile(apiKey, uploadResult.id)

            assertEquals(uploadResult.id, deleteResult.id)
            assertTrue(deleteResult.deleted, "deleted 应为 true")

            println("========== deleteFile ==========")
            println("id: ${deleteResult.id}")
            println("deleted: ${deleteResult.deleted}")
        } finally {
            tempFile.delete()
        }
    }
}
