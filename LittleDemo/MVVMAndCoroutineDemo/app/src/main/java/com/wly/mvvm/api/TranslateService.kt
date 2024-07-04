package com.wly.mvvm.api


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface TranslateService {


    @GET("openapi.do?keyfrom=imoocdict123456&key=324273592&type=data&doctype=json&version=1.1&q=blue")
    fun  getTranslateByGet() : Call<ResponseBody>

    @FormUrlEncoded
    @POST("openapi.do")
    fun getTranslateByPost1(
        @Field("keyform") param1: String,
        @Field("key")param2: String,
        @Field("type") param3: String,
        @Field("dectype")param4: String,
        @Field("version") param5: String,
        @Field("q")param6: String)
    : Call<ResponseBody>

    @FormUrlEncoded
    @POST("openapi.do")
    fun getTranslateByPost1(@FieldMap map: Map<String,String>)




}