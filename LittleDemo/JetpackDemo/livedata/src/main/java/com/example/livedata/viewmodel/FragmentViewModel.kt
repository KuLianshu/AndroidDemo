package com.example.livedata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentViewModel: ViewModel() {
    private var progress: MutableLiveData<Int> ? = null
    fun getProgress(): MutableLiveData<Int>?{
        if (progress == null){
            progress = MutableLiveData<Int>()
            progress!!.value = 0
        }
        return progress
    }
}