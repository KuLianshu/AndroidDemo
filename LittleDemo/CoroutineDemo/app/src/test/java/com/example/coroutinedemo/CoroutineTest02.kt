package com.example.coroutinedemo

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutineTest02 {

    @Test
    fun `test coroutine builder`() = runBlocking {
        val job1 = launch {
            delay(2000)
            println("job1 finished")
        }
        val job2 = async {
            delay(2000)
            println("job2 finished")
            "job2 result"
        }
        println("===== test =======")
        println(job2.await())

    }

    /*
     *  join 和 await 是挂起函数，不阻塞主线程
     */

    @Test
    fun `test coroutine join`() = runBlocking {
        val job1 = launch {
            delay(2000)
            println("One")
        }
        //等job1执行完后，再执行job2、job3
        job1.join()
        val job2 = launch {
            delay(200)
            println("Two")
        }
        val job3 = launch {
            delay(200)
            println("Three")
        }
    }

    @Test
    fun `test coroutine await`() = runBlocking {
        val job1 = async {
            delay(2000)
            println("One")
        }
        //等job1执行完后，再执行job2、job3
        job1.await()
        val job2 = async {
            delay(200)
            println("Two")
        }
        val job3 = async {
            delay(200)
            println("Three")
        }
    }

    @Test
    fun `test sync`() = runBlocking {

        val time = measureTimeMillis {
            val one = doOne()
            val two = doTwo()
            println("The result: ${one+two}")
        }
        println("Completed in $time ms")
    }

    @Test
    fun `test combine sync`() = runBlocking {
        val time = measureTimeMillis {
            val one = async { doOne() }
            val two = async { doTwo() }
            println("The result: ${one.await()+two.await()}")

//            /*不能这样写，耗时又变成2000ms了*/
//            val one = async { doOne() }.await()
//            val two = async { doTwo() }.await()
//            println("The result: ${one+two}")
        }
        println("Completed in $time ms")
    }

    private suspend fun doOne(): Int{
        delay(1000)
        println("doOne working")
        return 14
    }

    private suspend fun doTwo(): Int{
        delay(1000)
        println("doTwo working")
        return 25
    }

    @Test
    fun `test coroutine scope sync`() = runBlocking {
        coroutineScope {
            val job1 = launch {
                delay(400)
                println("job1 finished")
            }
            val job2 = async {
                delay(200)
                println("job2 finished")
                throw java.lang.IllegalArgumentException()
            }
        }

    }

    @Test
    fun `test supervisor scope sync`() = runBlocking {
        supervisorScope {
            val job1 = launch {
                delay(400)
                println("job1 finished")
            }
            val job2 = async {
                delay(200)
                println("job2 finished")
                throw java.lang.IllegalArgumentException()
            }
        }

    }

}