package com.example.demo.s2
/**
 * Kotlin 的it关键字
 */
fun main(){
    val methodAction : (Int, Int, Int) -> String = { n1,n2,n3 ->
       val number = 9999
        println("$number 参数1:$n1,参数2:$n2,参数3:$n3")
        "finish"
    }

    methodAction(1,2,3)

    val methodAction2 : (String) -> String = {"$it hhhh"}
    println(methodAction2("wly"))

    val methodAction3 : (Double) -> String = {"$it hhhh"}
    println(methodAction3(3.32))


}















