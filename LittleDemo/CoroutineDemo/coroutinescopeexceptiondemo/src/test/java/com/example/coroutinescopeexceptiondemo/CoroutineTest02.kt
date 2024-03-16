package com.example.coroutinescopeexceptiondemo


import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.junit.Test
import java.io.IOException

class CoroutineTest02 {

    @Test
    fun `test CoroutineContext`() = runBlocking<Unit> {
        launch(Dispatchers.Default+CoroutineName("Name")) {
            println("I'm working in thread ${Thread.currentThread().name}")
        }

    }

    /*
    打印结果如下
    1 "test#2":StandaloneCoroutine{Active}@43f23402 DefaultDispatcher-worker-1 @test#2
    2 "test#3":DeferredCoroutine{Active}@2654b9dd DefaultDispatcher-worker-3 @test#3
    可以看到创建了新的Job()；都是Dispatchers.IO调度器；协程名字都是test，当然，协程带编号。
     */
    @Test
    fun `test CoroutineContext extend`() = runBlocking<Unit> {
        val scope = CoroutineScope(Job()+Dispatchers.IO+CoroutineName("test"))
        val job = scope.launch {
            println("1 ${coroutineContext[Job]} ${Thread.currentThread().name}")
            val result = async {
                println("2 ${coroutineContext[Job]} ${Thread.currentThread().name}")
                "OK"
            }.await()
        }
        job.join()
    }

    @Test
    fun `test CoroutineContext extend2`() = runBlocking<Unit> {
        val coroutineException = CoroutineExceptionHandler{_,exception->
            println("Caught $exception")
        }
        val scope = CoroutineScope(Job()+Dispatchers.Main+coroutineException)
        val job = scope.launch(Dispatchers.IO) {
            println("1 ${coroutineContext[Job]} ${Thread.currentThread().name}")
            //新协程
        }
        job.join()
    }

    @Test
    fun `test exception propagation`() = runBlocking<Unit> {
        val job = GlobalScope.launch {
            //launch的异常在这里抛出
            throw IndexOutOfBoundsException()
        }
        job.join()

        val deferred = GlobalScope.async {
            throw java.lang.ArithmeticException()
        }
        //async的异常在这里抛出，如果没有await()则不抛出
        deferred.await()
        delay(1000)
    }

    @Test
    fun `test exception propagation2`() = runBlocking<Unit> {
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            async {
                //子协程的异常会被立即抛出
                throw java.lang.ArithmeticException()
            }
        }
        job.join()
    }

    /**
     * Job1抛出异常，不会影响到Job2
     */
    @Test
    fun `test SupervisorJob`() = runBlocking<Unit> {
        val supervisor = CoroutineScope(SupervisorJob())
        val job1 = supervisor.launch {
            delay(1000)
            println("child 1")
            throw java.lang.IllegalArgumentException()
        }
        val job2 = supervisor.launch {
            try {
                delay(Long.MAX_VALUE)
            }finally {
                println("child 2 finished")
            }
        }
        joinAll(job1,job2)
    }

    @Test
    fun `test supervisorScope`() = runBlocking<Unit> {
        supervisorScope {
            //这里抛出异常
            launch {
                delay(1000)
                println("child 1")
                throw IllegalArgumentException()
            }
            //这里正常运行不受影响
            try {
                delay(Long.MAX_VALUE)
            }finally {
                println("child 2 finished")
            }
        }
    }

    @Test
    fun `test supervisorScope2`() = runBlocking<Unit> {
        supervisorScope {
            launch {
                try {
                    println("The child is sleeping")
                    delay(Long.MAX_VALUE)
                }finally {
                    //因为主协程抛出了异常，所以子协程会停止，所以会执行到finally
                    println("The child is cancelled")
                }
            }
            //出让执行权
            yield()
            println("Throwing an exception from the scope")
            //这里抛出异常子，由launch构建的子协程也会跟随停止
            throw AssertionError()
        }
    }

    /**
     * 使用CoroutineExceptionHandler捕获异常
     * 虽然job、deferred在CoroutineScope的上下文中，
     * 但是deferred是用async构建的，所以只有
     * job中的异常会被捕获
     */
    @Test
    fun `test CoroutineExceptionHandler`() = runBlocking<Unit> {
        val handler = CoroutineExceptionHandler{_, exception ->
            println("Caught $exception")
        }
        val job = GlobalScope.launch(handler) {
            //这个异常会被捕获
            throw AssertionError()
        }
        val deferred = GlobalScope.async(handler) {
            //这个异常不会被捕获
            throw ArithmeticException()
        }
        job.join()
        deferred.await()
    }

    /**
     * 下面的异常会被捕获，原因如下
     * 1、在CoroutineScope的上下文当中
     * 2、由launch抛出
     */
    @Test
    fun `test CoroutineExceptionHandler2`() = runBlocking<Unit> {
        val handler = CoroutineExceptionHandler{_, exception ->
            println("Caught $exception")
        }
        val scope = CoroutineScope(Job())
        val job = scope.launch(handler) {
            launch {
                throw AssertionError()
            }
        }
        job.join()
    }

    @Test
    fun `test CoroutineExceptionHandler3`() = runBlocking<Unit> {
        val handler = CoroutineExceptionHandler{_, exception ->
            println("Caught $exception")
        }
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            //异常处理器放这里，将不会捕获到异常，因为launch的异常会抛向父协程
            launch(handler) {
                throw AssertionError()
            }
        }
        job.join()
    }

    /**
     * 下面三个println都会被打印到，即父协程并没有被取消，
     * 因为CancellationException，会被忽略掉。
     * 打印顺序如下
        child 。。。
        Cancelling child
        Child is cancel
        Parent is not cancelled
     */
    @Test
    fun `test cancel and exception`() = runBlocking<Unit> {
        val job = launch{
            val child = launch {
                try {
                    println("child 。。。")

                    //如果没有try{}catch(){}
                    //那么JobCancellationException这个异常就会被忽略
//                    try {
//                        delay(Long.MAX_VALUE)
//                    }catch (e: Exception){
//                        println("e: $e")
//                    }
                    //异常会被忽略，所以代码会执行到finally而不崩溃
                    delay(Long.MAX_VALUE)
                }finally {
                    println("Child is cancel")
                }
            }
            //这里父协程要让出执行权，子协程才有机会运行
            yield()
            println("Cancelling child")
            child.cancelAndJoin()
            yield()
            //父协程不会一同被取消，所以这行代码会被打印
            println("Parent is not cancelled")
        }
        job.join()
    }

    /**
     * 打印顺序如下
     Second child throws an exception
    Children are cancelled, but exception is not handled until all children terminate
    The first child finished its non cancellable block
    Caught java.lang.ArithmeticException
     */
    @Test
    fun `test cancel and exception2`() = runBlocking<Unit> {
        //两个子协程的异常都被取消后，异常才会被父协程处理。
        val handler = CoroutineExceptionHandler{_, exception ->
            println("Caught $exception")
        }

        val job = GlobalScope.launch(handler){
            launch {
                try {
                    delay(Long.MAX_VALUE)
                }finally {
                    withContext(NonCancellable){
                        //子协程都被取消了，但是异常还没有被处理
                        println("Children are cancelled, but exception is not handled until all children terminate")
                        delay(100)
                        println("The first child finished its non cancellable block")
                    }
                }
            }
            launch {
                delay(10)
                println("Second child throws an exception")
                throw ArithmeticException()
            }
        }
        job.join()
    }

    /**
     * 打印顺序如下
    Caught java.io.IOException [java.lang.ArithmeticException, java.lang.IndexOutOfBoundsException]
     */
    @Test
    fun `test exception aggregation`() = runBlocking<Unit> {
        //两个子协程的异常都被取消后，异常才会被父协程处理。
        val handler = CoroutineExceptionHandler{_, exception ->
            //这里捕捉了第一个异常，后续两个异常都绑定在第一个异常上，即绑定在exception上
            println("Caught $exception ${exception.suppressed.contentToString()}")
        }

        val job = GlobalScope.launch(handler){
            launch {
                try {
                    delay(Long.MAX_VALUE)
                }finally {
                    //第二个异常
                    throw ArithmeticException()
                }
            }
            launch {
                try {
                    delay(Long.MAX_VALUE)
                }finally {
                    //第三个异常
                    throw IndexOutOfBoundsException()
                }
            }
            launch {
                delay(10)
                //第一个异常
                throw IOException()
            }
        }
        job.join()
    }



}