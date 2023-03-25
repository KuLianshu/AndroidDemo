package com.example.demo.s2
/**
 * Kotlin 的函数类型&隐式返回
 */
fun main(){
    val methodAction : (Int, Int, Int) -> String = { number1,number2,number3 ->
       val inputVaalue = 9999
        "$inputVaalue 参数1:$number1,参数2:$number2,参数3:$number3"
    }


    println(methodAction(1,2,3))
}















