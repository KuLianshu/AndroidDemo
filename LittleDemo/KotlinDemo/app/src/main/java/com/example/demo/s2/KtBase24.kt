package com.example.demo.s2

import android.util.Log

/**
 * Kotlin 的函数类型&隐式返回
 */
fun main(){


    val methodAction : () ->String = {
        val  inputValue = 33333
        "$inputValue hhh"
    }


    println(methodAction())
}