package com.example.demo.s2
/**
 * Kotlin 的it关键字
 */
fun main(){

    val method1 = {v1:Double, v2:Float, v3:Int ->
        "v1:$v1,v2:$v2,v3:$v3"
    }

    println(method1(333.4,3.5f,55))

    val method2 = {
        134.8
    }

    println(method2())

}

fun printTest(){
    println("---------------1")
}















