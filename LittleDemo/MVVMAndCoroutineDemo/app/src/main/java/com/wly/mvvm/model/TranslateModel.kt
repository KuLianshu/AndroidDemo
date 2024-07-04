package com.wly.mvvm.model

import com.wly.mvvm.api.TranslateService
import com.wly.mvvm.base.BaseModel
import com.wly.mvvm.interfaces.OnTranslateCallBack
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class TranslateModel(onTranslateCallBack: OnTranslateCallBack)
    : BaseModel<OnTranslateCallBack>(onTranslateCallBack){

    private var mRetrofit: Retrofit
    private var mTranslateService: TranslateService
    private val mBaseUrl = "http://fanyi.youdao.com/"

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .build()
        mTranslateService = mRetrofit.create(TranslateService::class.java)
    }

    fun getTranslateData(){
        val getCall = mTranslateService.getTranslateByGet()
        getCall.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var result = "null"
                if (response.isSuccessful){
                    try {
                        result = response.body()?.string()?: "null"
                    }catch (e: IOException){
                        e.printStackTrace()
                    }
                }
                if (!isCallBackDestroy()){
                    getCallBack()?.onResponse(result)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (!isCallBackDestroy()){
                    getCallBack()?.onFailure(t)
                }
            }

        })
    }

}
