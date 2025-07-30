package com.example.myapplication.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * 下载单个文件
 */
class DownloadFileDemo {

    companion object RetrofitClient {
        private const val BASE_URL = "https://example.com/"

        private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val instance: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun test(){
        val fileUrl = "https://example.com/file.zip"
        downloadFile(fileUrl)
    }

    private fun downloadFile(fileUrl: String) {
        val service = RetrofitClient.instance.create(ApiService::class.java)
        val call = service.downloadFile(fileUrl)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    saveFile(response.body()!!)
                } else {
                    Log.e("FileDownload", "Failed to download file: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("FileDownload", "Error: ${t.message}")
            }
        })
    }

    private fun saveFile(responseBody: ResponseBody) {
        val filesDir = ""
        //指定文件保存路径
        val file = File(filesDir, "downloaded_file.zip")
        try {
            //在ResponseBody对象中获取输入流
            val inputStream = responseBody.byteStream()
            val outputStream = FileOutputStream(file)
            // 4KB buffer
            val buffer = ByteArray(4 * 1024)
            var read: Int
            //通过输出流把文件保存到设备
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
            Log.d("FileDownload", "File saved to ${file.absolutePath}")
        } catch (e: IOException) {
            Log.e("FileDownload", "Failed to save file: ${e.message}")
        }
    }
}