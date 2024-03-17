package com.example.coroutineflow.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

//创建Flow
//class MainViewModel: ViewModel() {
//    val timeFlow = flow {
//        var time = 0
//        while (true){
//            emit(time)
//            kotlinx.coroutines.delay(1000)
//            time++
//        }
//    }
//}


//创建StateFlow
//class MainViewModel: ViewModel() {
//    private val _stateFlow = MutableStateFlow(0)
//    val stateFlow = _stateFlow.asStateFlow()
//    fun startImer(){
//        val timer = Timer()
//        timer.scheduleAtFixedRate(object : TimerTask(){
//            override fun run() {
//                _stateFlow.value += 1
//            }
//        },0,1000)
//    }
//}


//class MainViewModel: ViewModel() {
//    private val timeFlow = flow {
//        var time = 0
//        while (true){
//            emit(time)
//            delay(1000)
//            time++
//        }
//    }
//    val stateFlow = timeFlow.stateIn(
//        viewModelScope,
//        //那么只要在5秒钟内横竖屏切换完成了，Flow就不会停止工作
//        SharingStarted.WhileSubscribed(5000),
//        0)
//}

//创建SharedFlow
class MainViewModel: ViewModel() {
    private val _loginFlow = MutableSharedFlow<String>()
    val loginFlow = _loginFlow.asSharedFlow()
    fun startLogin(){
        viewModelScope.launch {
            _loginFlow.emit("Login Success")
        }
    }
}
