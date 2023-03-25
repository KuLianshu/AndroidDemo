package com.example.demo.s2

import kotlin.math.roundToInt

/**
 * Kotlin 的it关键字
 */
fun main(){
//    val b = B()
//    b.printData("hhhh")
    printTest()
}

open class A{
    open fun printData(str: String){
        println("str:$str")
    }
}

class B :A(){

    override fun printData(str: String){
        super.printData("str:$str")
        println("==========")
    }
}

















