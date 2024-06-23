package com.hero.mvvmdemo.mvvm.model;

import com.hero.mvvmdemo.mvvm.api.TranslateService;
import com.hero.mvvmdemo.mvvm.base.BaseModel;
import com.hero.mvvmdemo.mvvm.interfaces.OnTranslateCallBack;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * <pre>
 *
 * </pre>
 */
public class TranslateModel extends BaseModel<OnTranslateCallBack> {
    /**
     * Retrofit模块 详解：https://blog.csdn.net/weixin_37730482/category_6875815.html
     */
    private Retrofit mRetrofit;
    private TranslateService mTranslateService;
    private String mBaseUrl = "http://fanyi.youdao.com/";

    public TranslateModel(OnTranslateCallBack onTranslateCallBack) {
        super(onTranslateCallBack);
        //获取Retrofit对象
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)//设置BaseUrl 必须以'/'结尾
                .build();
        //Retrofit接口请求
        mTranslateService = mRetrofit.create(TranslateService.class);
    }

    public void getTranslateData() {
        Call<ResponseBody> getCall = mTranslateService.getTranslateByGet();
        getCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;//报文 获取报文必须判断响应是否成功
                if (response.isSuccessful()) {
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!isCallBackDestory()) {
                    getCallBack().onResponse(result);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!isCallBackDestory()) {
                    getCallBack().onFailure(t);
                }
            }
        });
    }
}
