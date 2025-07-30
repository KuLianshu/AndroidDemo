package com.example.myapplication.okhttp

import okhttp3.*
import java.io.IOException

/**
 * 提交表单
 */
class SubmitFormDemo {
    fun main() {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("username", "kimi")
            .add("password", "123456")
            .add("age", "25")
            .build()

        val request = Request.Builder()
            .url("https://example.com/api/submitForm")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("请求失败: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    println("服务器返回错误: ${response.code}")
                    return
                }
                println("响应内容: ${response.body?.string()}")
            }
        })
    }
}