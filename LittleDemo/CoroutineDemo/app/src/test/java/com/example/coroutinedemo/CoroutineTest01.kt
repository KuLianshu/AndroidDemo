package com.example.coroutinedemo

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutineTest01 {

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
            delay(300)
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

    /**
     *
     * launch和 async创建的都是异步的协程作用域，所以，在runBlocking中，
     * 会先执行父协程作用域里的代码，所以标记三、标记四会被优先依次打印，然后才
     * 打印标记一和标记二。
     * 下面的执行结果是
     * 标记三, Test worker @coroutine#1
     * 标记四, Test worker @coroutine#1
     * 标记一, Test worker @coroutine#2
     * 标记二, Test worker @coroutine#3
     * 可以看到，job1、job2和父协程的线程都不一样
     */
    @Test
    fun `test async and launch test1`() = runBlocking {
        val job1 = launch {
//            delay(400)
            println("标记一, ${Thread.currentThread().name}")
        }
//        job1.join()
        val job2 = async {
//            delay(200)
            println("标记二, ${Thread.currentThread().name}")
        }
//        job2.await()
        println("标记三, ${Thread.currentThread().name}")
        println("标记四, ${Thread.currentThread().name}")
    }

    /**
     *
     * 这里test2和上面test1的执行逻辑是一样的，但是因为launch和 async
     * 创建的都是异步的协程作用域，而job1执行时间较长（delay(400)），
     * 所以job2先完成（只delay(200)），标记二会优先于标记一打印。
     * 下面的执行结果是
     * 标记三, Test worker @coroutine#1
     * 标记四, Test worker @coroutine#1
     * 标记二, Test worker @coroutine#3
     * 标记一, Test worker @coroutine#2
     */
    @Test
    fun `test async and launch test2`() = runBlocking {
        val job1 = launch {
            delay(400)
            println("标记一, ${Thread.currentThread().name}")
        }
        val job2 = async {
            delay(200)
            println("标记二, ${Thread.currentThread().name}")
        }
        println("标记三, ${Thread.currentThread().name}")
        println("标记四, ${Thread.currentThread().name}")
    }

    /**
     *
     * join可以让主协程后的内容等待launch协程体内的代码执行完再执行，
     * 所以标记二只能最后打印啦（标记三、四要等job1执行完的嘛）
     * 下面的执行结果是
     * 标记一, Test worker @coroutine#2
     * 标记三, Test worker @coroutine#1
     * 标记四, Test worker @coroutine#1
     * 标记二, Test worker @coroutine#3
     *
     */
    @Test
    fun `test async and launch test3`() = runBlocking {
        val job1 = launch {
            delay(400)
            println("标记一, ${Thread.currentThread().name}")
        }
        job1.join()
        val job2 = async {
            delay(200)
            println("标记二, ${Thread.currentThread().name}")
        }
        println("标记三, ${Thread.currentThread().name}")
        println("标记四, ${Thread.currentThread().name}")
    }

    /**
     *
     * await可以让主协程后的内容等待async协程体内的代码执行完再执行，
     * 所以下面就是顺序执行的啦
     * 下面的执行结果是
     * 标记一, Test worker @coroutine#2
     * 标记二, Test worker @coroutine#3
     * 标记三, Test worker @coroutine#1
     * 标记四, Test worker @coroutine#1
     */
    @Test
    fun `test async and launch test4`() = runBlocking {
        val job1 = launch {
            delay(400)
            println("标记一, ${Thread.currentThread().name}")
        }
        job1.join()
        val job2 = async {
            delay(200)
            println("标记二, ${Thread.currentThread().name}")
        }
        job2.await()
        println("标记三, ${Thread.currentThread().name}")
        println("标记四, ${Thread.currentThread().name}")
    }

    /**
     * 下面的打印是
     * 标记三, Test worker @coroutine#1
     * 标记四, Test worker @coroutine#1
     * 标记一, Test worker @coroutine#2
     * 标记二, Test worker @coroutine#3
     * 所以，不管子协程内有没有耗时操作，都会先执行子协程外的代码
     */
    @Test
    fun `test async and launch test5`() = runBlocking {
        launch {
            println("标记一, ${Thread.currentThread().name}")
        }
        async {
            println("标记二, ${Thread.currentThread().name}")
        }
        println("标记三, ${Thread.currentThread().name}")
        println("标记四, ${Thread.currentThread().name}")
    }
}