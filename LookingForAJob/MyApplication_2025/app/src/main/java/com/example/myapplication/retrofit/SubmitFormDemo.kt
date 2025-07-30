package com.example.myapplication.retrofit


import okhttp3.OkHttpClient
import okhttp3.ResponseBody

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 提交表单
 */
class SubmitFormDemo {

    companion object{

        private const val BASE_URL = "https://example.com/" // 替换为你的基础 URL

        private val client = OkHttpClient.Builder().apply {
            // 添加日志拦截器（可选）
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()

        val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    }


    fun test() {
        // 创建请求
        val call = apiService.submitForm(
            username = "kimi",
            password = "123456",
            age = 25
        )

        // 异步发起请求
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    println("响应成功: ${response.body()?.string()}")
                } else {
                    println("响应失败: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("请求失败: ${t.message}")
            }
        })

    }
}

