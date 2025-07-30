package com.example.myapplication.retrofit

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * 单个文件上传
 */
class SubmitFileDemo {
    companion object{
        private const val BASE_URL = "https://example.com/" // 替换为你的基础URL

        private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun test(){
        val filePath = "/path/to/your/file.jpg"
        uploadFile(filePath)
    }

    private fun uploadFile(filePath: String) {
        val file = File(filePath)
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val service = retrofit.create(ApiService::class.java)
        val call = service.uploadFile(body)

        call.enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("UploadSuccess", "File uploaded successfully: ${response.body()}")
                } else {
                    Log.e("UploadError", "Failed to upload file: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("UploadError", "Upload failed: ${t.message}")
            }
        })
    }
}
