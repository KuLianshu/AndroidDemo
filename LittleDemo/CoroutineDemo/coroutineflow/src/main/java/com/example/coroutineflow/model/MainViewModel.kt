package com.example.coroutineflow.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow

//创建Flow
class MainViewModel: ViewModel() {
    val timeFlow = flow {
        var time = 0
        while (true){
            emit(time)
            kotlinx.coroutines.delay(1000)
            time++
        }
    }
}