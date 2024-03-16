package com.example.coroutineflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutineTest03 {

    fun simpleFlow() = flow<Int> {
        for (i in 1..3){
            println("Emitting $i")
            emit(i)
        }
    }

    /**
     * 打印如下
    Emitting 1
    1
    Emitting 2
    2
    Caught java.lang.IllegalStateException: Collected 2
     */
    @Test
    fun `test flow exception`() = runBlocking<Unit>{
        //抓取下游的异常
        try {
            simpleFlow().collect(){value ->
                println(value)
                check(value<=1){"Collected $value"}
            }
        }catch (e: Throwable){
            println("Caught $e")
        }
    }


    /**
     * 打印如下
    Caughtjava.lang.ArithmeticException: Div 0
    1
    10
     */
    @Test
    fun `test flow exception2`() = runBlocking<Unit>{
        flow {
            emit(1)
            throw ArithmeticException("Div 0")
        }//抓取上游的异常
            .catch { e: Throwable ->
                println("Caught$e")
                //这里捕获到异常之后，可以重新发送数据
                emit(10)
            }
            .flowOn(Dispatchers.IO)
            .collect { println(it) }
    }


    fun simpleFlow2() = (1..3).asFlow()


    fun simpleFlow3() = flow<Int>{
        emit(1)
        throw RuntimeException()
    }



    /**
     * 打印如下
    1
    2
    3
    Done
     */
    @Test
    fun `test flow complete in finally`() = runBlocking<Unit>{
        try {
            simpleFlow2().collect { println(it) }
        }finally {
            println("Done")
        }
    }

    @Test
    fun `test flow complete in onCompletion`() = runBlocking<Unit>{
        /**
         * 打印如下
        1
        2
        3
        Done
         */
//        simpleFlow2()
//            .onCompletion { println("Done") }
//            .collect { println(it) }


        /**
         * 打印如下
        1
        Flow completed exceptionally
        Caught java.lang.RuntimeException
         */
//        simpleFlow3()
//                //onCompletion 可以获取异常信息，但是不会捕获异常
//            .onCompletion {exception ->
//                if (exception != null)
//                println("Flow completed exceptionally")
//            }
//            .catch { exception -> println("Caught $exception") }
//            .collect { println(it) }


        simpleFlow2()
            //onCompletion 可以获取上下游的异常信息，但是不会捕获异常
            .onCompletion {exception ->
                if (exception != null)
                    println("Flow completed exceptionally")
            }
            .collect { value ->
                println(value)
                check(value <= 1) { println("Collected $value") }
            }

    }

}











