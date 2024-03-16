package com.example.coroutineflow.demo

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


/*
打印结果
2
5
 */
fun main(){
    runBlocking {
        flow{
            //1、2直接间隔不够500毫秒，所以不发1
            emit(1)
            emit(2)
            //2、3之间间隔600>500，所以2可以发送
            delay(600)
            //3 4之间间隔<500，所以3不发
            emit(3)
            delay(100)
            //4 5之间间隔<500，所以4不发
            emit(4)
            delay(100)
            //5是最后一条，所以5发
            emit(5)
            //两条数据之间间隔500毫秒才能发送成功
            //要不要发当前的数据，取决于它与下一条数据间时间间隔够不够500
        }.debounce(500)
            .collect{
            println(it)
        }
    }
}
