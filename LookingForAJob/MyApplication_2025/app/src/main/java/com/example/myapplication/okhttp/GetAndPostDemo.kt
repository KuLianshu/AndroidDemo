package com.example.myapplication.okhttp

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

/**
 * GET/POST请求示例
 */
class GetAndPostDemo {

    fun getTest() {
        // 1. 创建 OkHttpClient 对象，并设置连接、读取、写入超时限制，还有拦截器
        val client = OkHttpClient.Builder()
            .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(CustomInterceptor())
            .build()
        // 2. 构造 Request 对象，用 new Request.Builder() 的方式建立，并设置服务器地址 url
        val request = Request.Builder()
            .url("https://www.example.com")
            .build()
        // 3. 通过前两步创建 Call 对象
        val call = client.newCall(request)
        // 4. 通过 call.enqueue() 来提交异步请求
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val responseData = response.body?.string()
                    println("Response: $responseData")
                }
            }
        })
    }

    fun postTest() {
        // 创建OkHttpClient实例
        val client = OkHttpClient()
        // 创建请求体（JSON格式）
        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            "{\"name\":\"Kimi\",\"age\":25}"
        )
        // 创建POST请求
        val request = Request.Builder()
            .url("https://example.com/api/submit") // 替换为你的目标URL
            .post(requestBody)
            .build()
        // 发起请求
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
        // 防止主线程退出，等待异步请求完成（实际开发中应避免阻塞主线程）
        Thread.sleep(5000)
    }

}