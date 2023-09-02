package com.example.coroutinedemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinedemo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    var userLiveData = MutableLiveData<String>()

    private val userRepository by lazy { UserRepository() }

    fun getUser(){
        //如果下面用了Retrofit，就不用加Dispatchers.IO了
        //因为Retrofit识别到耗时操作，会自动创建协程
        viewModelScope.launch {

            userLiveData.value =  async(Dispatchers.IO) {
                userRepository.getUser()
            }.await()

        }
    }

}