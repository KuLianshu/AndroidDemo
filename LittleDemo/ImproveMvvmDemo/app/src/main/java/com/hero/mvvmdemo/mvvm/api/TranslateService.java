package com.hero.mvvmdemo.mvvm.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 翻译接口
 */
public interface TranslateService {

    /**
     * 翻译模块Get请求
     */
    @GET("openapi.do?keyfrom=imoocdict123456&key=324273592&type=data&doctype=json&version=1.1&q=blue")
    Call<ResponseBody> getTranslateByGet();

    /**
     * 翻译模块Post请求 Field方式 单个参数逐一传参
     */

    @FormUrlEncoded
    @POST("openapi.do")
    Call<ResponseBody> getTranslateByPost(@Field("keyfrom") String param1, @Field("key") String params2, @Field("type") String param3, @Field("doctype") String param4, @Field("version") String param5, @Field("q") String param6);

    /**
     * 翻译模块Post请求 FieldMap 集合传参
     */
    @FormUrlEncoded
    @POST("openapi.do")
    Call<ResponseBody> getTranslateByPost(@FieldMap Map<String, String> map);

}