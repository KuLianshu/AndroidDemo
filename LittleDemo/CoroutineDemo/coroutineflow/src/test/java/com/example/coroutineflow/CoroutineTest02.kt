package com.example.coroutineflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutineTest02 {

    suspend fun performRequest(request: Int): String{
        delay(1000)
        return "response $request"
    }

    @Test
    fun `test transform flow operator`() = runBlocking<Unit> {
        /*
        打印
        response 1
        response 2
        response 3
         */
        (1..3).asFlow()
            .map { request-> performRequest(request) }
            .collect { value -> println(value) }

        /*
        打印
        Making request 1
        response 1
        Making request 2
        response 2
        Making request 3
        response 3
         */
        (1..3).asFlow()
            .transform {request->
                emit("Making request $request")
                emit(performRequest(request))
            }
            .collect { value -> println(value) }
    }

    fun numbers()  = flow<Int> {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        }finally {
            println("Finally in numbers")
        }
    }

    @Test
    fun `test limit length operator`()= runBlocking {
        //打印 ：12Finally in numbers
        // 只收集到1和2，因为这里加了限长操作符take
        numbers().take(2).collect { value-> print(value) }
    }


    @Test
    fun `test terminal operator`()= runBlocking {
        val sum = (1..5).asFlow()
            .map { it*it }
                //将所有结果相加，即 1*1+2*2+3*3+4*4+5*5
            .reduce { a,b->a+b  }
        //打印：55
        println(sum)
    }


    /**
     * 打印
    1 -> One
    2 -> Two
    3 -> Three
     */
    @Test
    fun `test zip`()= runBlocking {
        val numbs = (1..3).asFlow()
        val strs = flowOf("One","Two","Three")
        numbs.zip(strs){a,b-> " $a -> $b"}.collect { println(it) }
    }

    /**
     * 打印
    1 -> One at 438 ms form start
    2 -> Two at 849 ms form start
    3 -> Three at 1258 ms form start
     */
    @Test
    fun `test zip2`()= runBlocking {
        //下面两个流是异步的，所以第一次接收流，时间间隔是400ms
        val numbs = (1..3).asFlow().onEach { delay(300) }
        val strs = flowOf("One","Two","Three").onEach{ delay(400) }
        val startIme = System.currentTimeMillis()
        numbs.zip(strs){a,b-> " $a -> $b"}.collect {
            println("$it at ${System.currentTimeMillis() - startIme} ms form start")
        }
    }


    fun requestFlow(i: Int) = flow<String> {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }

    /**
     （1）用map的打印
     kotlinx.coroutines.flow.SafeFlow@68034211 at 144 ms form start
     kotlinx.coroutines.flow.SafeFlow@1b8a29df at 263 ms form start
     kotlinx.coroutines.flow.SafeFlow@4fbe37eb at 370 ms form start
     (2)用flatMapConcat的打印
     1: First at 153 ms form start
     1: Second at 665 ms form start
     2: First at 773 ms form start
     2: Second at 1285 ms form start
     3: First at 1395 ms form start
     3: Second at 1909 ms form start
     (3)用flatMapMerge的打印
     1: First at 173 ms form start
     2: First at 273 ms form start
     3: First at 382 ms form start
     1: Second at 674 ms form start
     2: Second at 782 ms form start
     3: Second at 894 ms form start
     (4)用flatMapLatest的打印
     1: First at 179 ms form start
     2: First at 368 ms form start
     3: First at 485 ms form start
     3: Second at 998 ms form start
     */
    @Test
    fun `test flatMapConcat`()= runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100) }
            //Flow<Flow<String>>
//            .map { requestFlow(it) }
//            .flatMapConcat { requestFlow(it) }
//            .flatMapMerge { requestFlow(it) }
            .flatMapLatest { requestFlow(it) }
            .collect{
                println("$it at ${System.currentTimeMillis() - startTime} ms form start")
            }

    }

}











