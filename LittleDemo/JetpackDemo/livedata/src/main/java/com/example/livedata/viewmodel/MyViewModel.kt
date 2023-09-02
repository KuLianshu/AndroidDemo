package com.example.livedata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {

    private  var currentSecond : MutableLiveData<Int> ? = null

    fun  getCurrentSecond(): MutableLiveData<Int>?{
        if (currentSecond == null){
            currentSecond = MutableLiveData<Int>()
            currentSecond!!.postValue(0)
        }
        return currentSecond
    }



}