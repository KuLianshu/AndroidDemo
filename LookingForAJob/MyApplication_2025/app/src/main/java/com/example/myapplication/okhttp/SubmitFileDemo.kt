package com.example.myapplication.okhttp

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.IOException

/**
 * 单个文件上传
 */
class SubmitFileDemo {

    fun test() {
        val filePath = "path/to/your/file.txt"
        val uploadUrl = "https://example.com/upload"
        uploadFile(filePath, uploadUrl)
    }

    private val client = OkHttpClient()
    fun uploadFile(filePath: String, uploadUrl: String) {
        val file = File(filePath)
        if (!file.exists()) {
            println("文件不存在: $filePath")
            return
        }

        // 创建RequestBody，指定文件类型
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file))
            .build()

        val request = Request.Builder()
            .url(uploadUrl)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            println("服务器响应: ${response.body?.string()}")
        }
    }
}

