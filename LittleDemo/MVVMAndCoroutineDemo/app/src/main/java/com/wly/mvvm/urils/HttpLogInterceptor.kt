package com.wly.mvvm.urils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit


class HttpLogInterceptor : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body
        var body: String? = null
        requestBody?.let {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset: Charset? = UTF8
            val contentType = requestBody.contentType()
            contentType?.let {
                charset = contentType.charset(UTF8)
            }
            body = buffer.readString(charset!!)
        }

//        LogUtil.i(
//            "发送请求: method：" + request.method
//                    + "\nurl：" + request.url
//                    + "\n请求头：" + request.headers
//                    + "\n请求参数: " + body)

        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body
        val rBody: String

        val source = responseBody!!.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()

        var charset: Charset? = UTF8
        val contentType = responseBody.contentType()
        contentType?.let {
            try {
                charset = contentType.charset(UTF8)
            } catch (e: UnsupportedCharsetException) {
                e.message?.let { it1 -> Log.e("WLY",it1) }
            }
        }
        rBody = buffer.clone().readString(charset!!)

//        LogUtil.i(
//            "收到响应: code:" + response.code
//                    + "\n请求url：" + response.request.url
//                    + "\n请求body：" + body
//                    + "\nResponse: " + rBody)

        return response
    }
}
