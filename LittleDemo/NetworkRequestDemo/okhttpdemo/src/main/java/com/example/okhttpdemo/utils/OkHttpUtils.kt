package com.example.okhttpdemo.utils

import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class OkHttpUtils {

    val TAG = "WLY"
    val baseUrl = "https://www.wanandroid.com"

    fun get(){
        val url = "https://www.wanandroid.com//hotkey/json"
        val request = Request.Builder()
            .url(url)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG,"e : ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    response.body()?.let {
                        //这里it.string()就是服务器返回的JSON串了
                        val json = it.string()
                        val responseData = Gson().fromJson(json,ResponseData::class.java)
                        Log.i(TAG,responseData.toString())

                    }
                }
            }
        })
    }

    /**
     * 异步下载文件
     */
    private fun asyncDownloadFile() {
        val url = ""
        //创建request请求对象
        val request = Request.Builder()
            .url(url)
            .build()

        //创建call并调用enqueue()方法实现网络请求
        OkHttpClient().newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful){
                        //响应成功后，从response获取输入流，写入到文件中
                        val inputStream=response.body()?.byteStream()
                        val filePath= Environment.getExternalStorageDirectory().absolutePath
                        val file= File(filePath,"test.txt")
                        val fileOutputStream= FileOutputStream(file)
                        val buffer=ByteArray(2048)
                        do {
                            val len= inputStream?.read(buffer)
                            if (len != null) {
                                fileOutputStream.write(buffer,0,len)
                            }
                        }while (len!=-1)
                        fileOutputStream.flush()
                    }

                }
            })
    }


    data class Hotkey(val id: Int, val link: String, val name: String, val order: Int, val visible: Int)
    data class ResponseData(val data: List<Hotkey>, val errorCode: Int, val errorMsg: String)

}