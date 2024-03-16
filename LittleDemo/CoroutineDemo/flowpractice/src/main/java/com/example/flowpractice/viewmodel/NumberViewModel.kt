package com.example.flowpractice.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class NumberViewModel : ViewModel(){

    val number = MutableStateFlow(0)

    fun increment(){
        number.value++
    }

    fun decrement(){
        number.value--
    }

}