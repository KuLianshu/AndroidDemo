package com.wly.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wly.mvvm.base.BaseViewModel
import com.wly.mvvm.bean.TranslateBean
import com.wly.mvvm.interfaces.OnTranslateCallBack
import com.wly.mvvm.model.TranslateModel

class TranslateViewModel : BaseViewModel<TranslateModel>(), OnTranslateCallBack{

    private val mTranslateData = MutableLiveData<TranslateBean>()
    private val mLoadingLiveData = MutableLiveData<Boolean>()

    init {
        initData()
    }

    override fun createModel(): TranslateModel {
        return TranslateModel(this)
    }

    private fun initData(){
        val translateBean = TranslateBean("可编辑的标题","点击看看","数据")
        mTranslateData.value = translateBean
        mLoadingLiveData.value = false
    }

    fun requestTranslateData(){
        mLoadingLiveData.value = true
        model?.getTranslateData()
    }

    fun getMTranslateData(): MutableLiveData<TranslateBean>{
        return mTranslateData
    }

    fun getLoadingLiveData(): MutableLiveData<Boolean>{
        return mLoadingLiveData
    }

    override fun onResponse(response: String) {
        mLoadingLiveData.value = false
        val value = mTranslateData.value
        value?.let {
            it.data = response
            mTranslateData.postValue(it)
        }
    }

    override fun onFailure(t: Throwable) {
        mLoadingLiveData.value = false
        val value = mTranslateData.value
        value?.let {
            it.data = "error: ${t.message}"
            mTranslateData.postValue(it)
        }
    }
}