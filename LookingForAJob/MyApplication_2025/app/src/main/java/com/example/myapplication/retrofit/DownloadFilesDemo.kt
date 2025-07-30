package com.example.myapplication.retrofit

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * 下载多个文件
 */
class DownloadFilesDemo {

    companion object RetrofitClient {
        private val client = OkHttpClient.Builder().build()
        val apiService: ApiService = Retrofit.Builder()
            .baseUrl("https://example.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


    private suspend fun downloadMultipleFiles(fileUrls: List<String>) {
        fileUrls.forEach { url ->
            downloadFile(url)
        }
    }

    private suspend fun downloadFile(fileUrl: String) {
        val filesDir = ""
        val response = apiService.downloadFileList(fileUrl)
        if (response.isSuccessful && response.body() != null) {
            val file = File(filesDir, fileUrl.substringAfterLast('/'))
            saveFile(response.body()!!, file)
            Log.d("Download", "File downloaded: ${file.absolutePath}")
        } else {
            Log.e("Download", "Failed to download file: $fileUrl")
        }
    }

    private suspend fun saveFile(responseBody: ResponseBody, file: File) {
        withContext(Dispatchers.IO) {
            val inputStream: InputStream = responseBody.byteStream()
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.close()
            inputStream.close()
        }
    }

    suspend fun test(){
        coroutineScope {
            // 启动协程下载多个文件
            launch(Dispatchers.IO) {
                val fileUrls = listOf(
                    "https://example.com/file1.jpg",
                    "https://example.com/file2.jpg",
                    "https://example.com/file3.jpg"
                )
                downloadMultipleFiles(fileUrls)
            }
        }
    }

}