package com.wly.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wly.mvvm.bean.TranslateBean
import com.wly.mvvm.bean.TranslateStatus
import com.wly.mvvm.model.NewTranslateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class NewTranslateViewModel : ViewModel(){

    private val mTranslateData = MutableLiveData<TranslateBean>()
    private val mLoadingLiveData = MutableLiveData<Boolean>()

    private lateinit var model: NewTranslateModel

    init {
        initData()
    }

    private fun initData(){
        model = NewTranslateModel()
        val translateBean = TranslateBean("可编辑的标题","点击看看","数据")
        mTranslateData.value = translateBean
        mLoadingLiveData.value = false
    }

    fun requestTranslateData(){
        mLoadingLiveData.value = true
        viewModelScope.launch {
            //ViewModel层获取到Model层的数据并处理
            model.getTranslateData().collect{status->
                launch (Dispatchers.Main){
                    when(status){
                        is TranslateStatus.Success->{
                            mLoadingLiveData.value = false
                            val value = mTranslateData.value
                            value?.let {
                                it.data = status.data
                                mTranslateData.postValue(it)
                            }
                        }
                        is TranslateStatus.Failure->{
                            mLoadingLiveData.value = false
                            val value = mTranslateData.value
                            value?.let {
                                it.data = "error: ${status.message}"
                                mTranslateData.postValue(it)
                            }
                        }
                        else->{}
                    }
                }

            }
        }
    }

    fun getMTranslateData(): MutableLiveData<TranslateBean>{
        return mTranslateData
    }

    fun getLoadingLiveData(): MutableLiveData<Boolean>{
        return mLoadingLiveData
    }


}