package com.wly.mvvm.urils

import com.wly.mvvm.api.TranslateService
import okhttp3.OkHttpClient
import org.apache.http.conn.ssl.AllowAllHostnameVerifier
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object OkHttpUtils {

    val mTranslateService: TranslateService by lazy {
//        val logging = HttpLogInterceptor()
        val client  = OkHttpClient.Builder()
            .hostnameVerifier(AllowAllHostnameVerifier())
                //加拦截器
//            .addInterceptor(logging)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val mBaseUrl = "http://fanyi.youdao.com/"
        val mRetrofit= Retrofit.Builder()
            .client(client)
            .baseUrl(mBaseUrl)
            .build()
        mRetrofit.create(TranslateService::class.java)
    }

}
