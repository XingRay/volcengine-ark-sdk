package com.github.xingray.volcenginesdk.api

import com.github.xingray.volcenginesdk.ArkConstants

import com.github.xingray.volcenginesdk.ArkClient
import com.github.xingray.volcenginesdk.model.file.*
import com.github.xingray.volcenginesdk.util.checkSuccess
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.io.File

/**
 * 文件管理 API。
 *
 * 支持文件上传、查询、列出和删除操作。上传的文件可用于文件提取、
 * 微调训练等场景。
 *
 * API 端点:
 * - POST   /files - 上传文件
 * - GET    /files/{fileId} - 查询文件信息
 * - GET    /files - 列出文件
 * - DELETE /files/{fileId} - 删除文件
 *
 * @param client ArkClient 实例
 */
class FileApi(private val client: ArkClient) {

    /**
     * 上传文件。
     *
     * 通过 multipart/form-data 方式上传文件到服务端。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param file 要上传的本地文件
     * @param purpose 文件用途，默认 "file-extract"
     * @return 文件元数据
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun uploadFile(apiKey: String, file: File, purpose: String = "file-extract"): FileMeta {
        val response = client.httpClient.post("${client.baseUrl}/files") {
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            setBody(MultiPartFormDataContent(
                formData {
                    append("purpose", purpose)
                    append("file", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                        append(HttpHeaders.ContentType, ContentType.Application.OctetStream.toString())
                    })
                }
            ))
        }
        response.checkSuccess()
        return response.body()
    }

    /**
     * 查询文件信息。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param fileId 文件唯一标识
     * @return 文件元数据
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun retrieveFile(apiKey: String, fileId: String): FileMeta {
        val response = client.get(apiKey, "${ArkConstants.Endpoint.FILES}/$fileId")
        response.checkSuccess()
        return response.body()
    }

    /**
     * 列出文件。
     *
     * @param apiKey 用于鉴权的 API Key
     * @return 文件列表响应
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun listFiles(apiKey: String): ListFilesResponse {
        val response = client.get(apiKey, ArkConstants.Endpoint.FILES)
        response.checkSuccess()
        return response.body()
    }

    /**
     * 删除文件。
     *
     * @param apiKey 用于鉴权的 API Key
     * @param fileId 文件唯一标识
     * @return 删除结果
     * @throws io.github.xingray.volcenginesdk.ArkException 当 API 返回错误时
     */
    suspend fun deleteFile(apiKey: String, fileId: String): DeleteFileResponse {
        val response = client.delete(apiKey, "${ArkConstants.Endpoint.FILES}/$fileId")
        response.checkSuccess()
        return response.body()
    }
}
