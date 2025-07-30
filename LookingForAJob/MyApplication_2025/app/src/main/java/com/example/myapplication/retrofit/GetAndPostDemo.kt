package com.example.myapplication.retrofit

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * GET/POST请求
 */
class GetAndPostDemo {
    //创建OKHttpClient对象
    val okHttpClient = OkHttpClient.Builder()
        // 设置连接超时时间
        .connectTimeout(10, TimeUnit.SECONDS)
        // 设置读取超时时间
        .readTimeout(15, TimeUnit.SECONDS)
        // 设置写入超时时间
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    //创建Retrofit对象
    val retrofit = Retrofit.Builder()
        // 设置服务器地址
        .baseUrl("https://api.example.com/")
        // 添加上面创建的 OkHttpClient
        .client(okHttpClient)
        // 添加 Gson 转换器
        .addConverterFactory(GsonConverterFactory.create())
        //添加RxJava适配器
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    //创建apiService对象
    val apiService = retrofit.create(ApiService::class.java)

    //发起同步请求
    fun getTest(){
        try {
            val response = apiService.getData("exampleParam").execute()
            if (response.isSuccessful) {
                println("Response: ${response.body()}")
            } else {
                println("Request failed: ${response.code()}")
            }
        }catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }

        //发起异步请求
        fun postTest(){
            apiService.getData("key","exampleParam").enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                    if (response.isSuccessful) {
                        println("Response: ${response.body()}")
                    } else {
                        println("Request failed: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        }
}