package com.example.myapplication.retrofit

import com.example.myapplication.bean.Post
import com.example.myapplication.mvvm.data.model.User
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    // 示例：GET 请求
    @GET("data")
    fun getData(@Query("param") param: String): Call<String>

    // 示例：POST 请求
    @POST("submit")
    fun getData(@Query("key") key: String, @Query("value") value: String): Call<String>

    @FormUrlEncoded
    @POST("api/submitForm")
    fun submitForm(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("age") age: Int
    ): Call<ResponseBody>

    @Multipart
    @POST("upload")
    fun uploadFile(@Part file: MultipartBody.Part): Call<String>


    @Multipart
    @POST("upload")
    fun uploadImages(@Part images: List<MultipartBody.Part>): Call<ResponseBody>

    //@Streaming必不可少
    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>

    @Streaming
    @GET
    suspend fun downloadFileList(@Url fileUrl: String): Response<ResponseBody>

    @GET("posts/1")
    fun getPost(): Observable<Post>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): User


}