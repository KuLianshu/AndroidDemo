package com.example.coroutinedemo

import android.util.Log
import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutineTest02 {

    @Test
    fun `test coroutine cancel`() = runBlocking<Unit> {
        val scope = CoroutineScope(Dispatchers.Default)
        val job1 = scope.launch {
                delay(1000)
                println("job 1")
            }
        val job2 = scope.launch {
                delay(1000)
                println("job 2")
            }
        //父协程并不会等待子协程job1、job2执行
        //因为这里子协程有自己的协程作用域
        //所以必须在父协程里手动加delay，才能看到子协程打印
        delay(100)
//        scope.cancel()
        delay(2000)
    }


    @Test
    fun `test brother cancel`() = runBlocking<Unit> {
        val scope = CoroutineScope(Dispatchers.Default)
        val job1 = scope.launch {
            delay(1000)
            println("job 1")
        }
        val job2 = scope.launch {
            delay(1000)
            println("job 2")
        }
        delay(100)
        //被取消的子协程并不会影响其余兄弟协程，所以job2会正常打印
//        job1.cancel()
        delay(1000)
    }

    @Test
    fun `test CancellationException`() = runBlocking<Unit> {
        val job1 = GlobalScope.launch {
            try{
                delay(1000)
                println("job 1")
            }catch (e: Exception){
//                e.printStackTrace()
                println("e: "+e.message)
            }
        }
        delay(100)
        //异常会网上抛给父类，也可以手动try_catch
//        job1.cancel(CancellationException("取消"))
        //cancel参数可带可不带
//        job1.cancel()
        //这里job1有自己的协程作用域
        //所以父协程并不会等待job1执行
        //要想看到job打印，这里必须手动加入join()
        //让父协程等待job1执行
        //当然job1被执行的前提是，没有被cancel掉
        job1.join()

    }

//    @Test
//    fun `test cancel cpu task by isActive`() = runBlocking<Unit> {
//        val startTime = System.currentTimeMillis()
//        val job = launch(Dispatchers.Default) {
//            var nextPrintTime = startTime
//            var i = 0
//            while (i < 5 && isActive ){
////                val current =  System.currentTimeMillis()
////                if (current >= nextPrintTime){
////                    Log.i("WLY","$current, $nextPrintTime")
//                if (System.currentTimeMillis()>=nextPrintTime){
////                    val current =  System.currentTimeMillis()
////                    Log.i("WLY","$current, $nextPrintTime")
//                    println("job: I'm sleeping ${i++} ...")
//                    nextPrintTime += 500
//                }
//            }
//        }
//        println("main: I'm tired of waiting!")
//        delay(1300)
//        job.cancelAndJoin()
//        println("main: Now I can quit.")
//    }

    @Test
    fun `test cancel cpu task by isActive`() = runBlocking<Unit> {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5 &&isActive){
                if (System.currentTimeMillis() >= nextPrintTime){
                    println("job: I'm sleeping ${i++} ... ${System.currentTimeMillis()- nextPrintTime}")
                    nextPrintTime += 500
                }
            }
        }
        println("main: I'm tired of waiting!")
        delay(1300)
        job.cancelAndJoin()
        println("main: Now I can quit. ${System.currentTimeMillis()-startTime}")
    }

    @Test
    fun `test cancel cpu task by ensureActive`() = runBlocking<Unit> {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5 ){
                ensureActive()
                if (System.currentTimeMillis() >= nextPrintTime){
                    println("job: I'm sleeping ${i++} ... ${System.currentTimeMillis()- nextPrintTime}")
                    nextPrintTime += 500
                }
            }
        }
        println("main: I'm tired of waiting!")
        delay(1300)
        job.cancelAndJoin()
        println("main: Now I can quit. ${System.currentTimeMillis()-startTime}")
    }

    @Test
    fun `test cancel cpu task by yield`() = runBlocking<Unit> {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5 ){
                yield()
                if (System.currentTimeMillis() >= nextPrintTime){
                    println("job: I'm sleeping ${i++} ... ${System.currentTimeMillis()- nextPrintTime}")
                    nextPrintTime += 500
                }
            }
        }
        println("main: I'm tired of waiting!")
        delay(1300)
        job.cancelAndJoin()
        println("main: Now I can quit. ${System.currentTimeMillis()-startTime}")
    }
}