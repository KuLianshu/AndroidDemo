package com.example.myapplication.okhttp

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.IOException

/**
 * 提交多个文件
 */
class SubmitFilesDemo {

        private val client = OkHttpClient()

        fun uploadFiles(filePaths: List<String>, url: String) {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)

            // 遍历文件路径列表，添加文件到请求体
            for (filePath in filePaths) {
                val file = File(filePath)
                if (file.exists()) {
                    val requestBodyPart = RequestBody.create(
                        "application/octet-stream".toMediaTypeOrNull(),
                        file
                    )
                    requestBody.addFormDataPart("file", file.name, requestBodyPart)
                } else {
                    println("文件不存在: $filePath")
                }
            }

            val request = Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("上传失败: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        println("服务器返回错误: ${response.code}")
                        return
                    }
                    println("上传成功: ${response.body?.string()}")
                }
            })
        }

    fun main() {

        val filePaths = listOf(
            "path/to/file1.txt",
            "path/to/file2.jpg"
        )
        val uploadUrl = "https://example.com/upload"

        uploadFiles(filePaths, uploadUrl)
    }
}

