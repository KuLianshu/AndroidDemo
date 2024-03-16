package com.example.coroutineflow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutineTest01 {

    fun simpleFlow()  = flow<Int> {
        for (i in 1..3){
            delay(1000)
            emit(i)
        }
    }

    @Test
    fun `test multiple values`() = runBlocking{
        launch {
            for (k in 1..3){
                println("I'm not blocked $k")
                delay(1500)
            }
        }
        simpleFlow().collect {value ->
            println(value)
        }
    }

    fun simpleFlow2()  = flow<Int> {
        println("Flow started")
        for (i in 1..3){
            delay(1000)
            emit(i)
        }
    }

    /**
     *打印结果如下
    Calling collect...
    Flow started
    1
    2
    3
    Calling collect again ...
    Flow started
    1
    2
    3
     */
    @Test
    fun `test flow is cold`() = runBlocking{
        //这一步，flow中的代码还没有被执行
        val flow = simpleFlow2()
        println("Calling collect...")
        //collect时，flow中的代码才会被执行
        flow.collect{value -> println(value) }
        println("Calling collect again ...")
        //这里flow中的代码将会被再次调用
        flow.collect{value -> println(value) }
    }

    /*
    打印结果如下
    Collect string 2
    Collect string 4
     */
    @Test
    fun `test flow continuation`() = runBlocking{
        //asFlow()可以快速构建流
        (1..5).asFlow().filter {
            it%2==0
        }.map {
            "string $it"
        }.collect {
            println("Collect $it")
        }
    }

    @Test
    fun `test flow builder`() = runBlocking{
        flowOf("one","two","three")
            .onEach { delay(1000) }
            .collect { println(it) }
        (1..3).asFlow().collect {
            println(it)
        }
    }


    fun simpleFlow3() = flow<Int>{
        println("Flow started ${Thread.currentThread().name}")
        for (i in 1..3){
            delay(1000)
            emit(i)
        }
    }


    /**
     * 打印如下
    Flow started Test worker @coroutine#1
    Collected 1 Test worker @coroutine#1
    Collected 2 Test worker @coroutine#1
    Collected 3 Test worker @coroutine#1
     */
    @Test
    fun `test flow context`() = runBlocking{
        simpleFlow3()
            .collect{value ->
                println("Collected $value ${Thread.currentThread().name}")
            }
    }

    fun simpleFlow4() = flow<Int>{
        withContext(Dispatchers.IO) {
            println("Flow started ${Thread.currentThread().name}")
            for (i in 1..3) {
                delay(1000)
                emit(i)
            }
        }
    }


    //下面会报错，因为发射Flow时的上下文和收集Flow时的上下文不一致
    @Test
    fun `test flow context1`() = runBlocking{
        simpleFlow4()
            .collect{value ->
                println("Collected $value ${Thread.currentThread().name}")
            }
    }


    fun simpleFlow5() = flow<Int>{
        println("Flow started ${Thread.currentThread().name}")
        for (i in 1..3){
            delay(1000)
            emit(i)
        }
    }.flowOn(Dispatchers.Default)

    /**
     *打印如下
    Flow started DefaultDispatcher-worker-2 @coroutine#2
    Collected 1 Test worker @coroutine#1
    Collected 2 Test worker @coroutine#1
    Collected 3 Test worker @coroutine#1
     */
    @Test
    fun `test flow on`() = runBlocking{
        simpleFlow5()
            .collect{value ->
                println("Collected $value ${Thread.currentThread().name}")
            }
    }

    fun events() = (1..3)
        .asFlow()
        .onEach { delay(100) }
        .flowOn(Dispatchers.Default)


    /**
     * 打印如下
    Event: 1 DefaultDispatcher-worker-1 @coroutine#2
    Event: 2 DefaultDispatcher-worker-1 @coroutine#2
    Event: 3 DefaultDispatcher-worker-1 @coroutine#2
     */
    @Test
    fun `test flow launch`() = runBlocking<Unit>{
        events()
            .onEach { event-> println("Event: $event ${Thread.currentThread().name}") }
//            .collect {  }
            .launchIn(CoroutineScope(Dispatchers.IO))
            .join()
    }

    fun simpleFlow6() = flow<Int>{
        for (i in 1..3) {
            delay(1000)
            emit(i)
            println("Emitting $i")
        }
    }

    /**
     * 打印如下
    1
    Emitting 1
    2
    Emitting 2
     */
    @Test
    fun `test cancel flow`() = runBlocking<Unit>{
        //2.5秒的时候取消这个流，所以不会打印3
        withTimeoutOrNull(2500){
            simpleFlow6().collect { value-> println(value) }
        }
        println("Downe")
    }


    fun simpleFlow7() = flow<Int>{
        for (i in 1..5) {
            emit(i)
            print("Emitting $i; ")
        }
    }

    @Test
    fun `test cancel check`() = runBlocking<Unit>{
//        /*
//         打印如下
//         1 Emitting 1; 2 Emitting 2; 3 Emitting 3;
//         只打印到3，流就被取消了，并且程序抛出异常
//         */
//        simpleFlow7().collect { value->
//            print("$value ")
//            if (value == 3) cancel()
//        }

        /*
        这里因为是密集型，所以无法取消。
        打印如下，可以打印到5，才抛出异常
         12345
         */
//        (1..5).asFlow().collect { value->
        //加上cancellable，打印到3时，流才会被取消，此时的打印为 123
        (1..5).asFlow().cancellable().collect { value->
            print(value)
            if (value == 3) cancel()
        }
    }

    fun simpleFlow8() = flow<Int>{
        for (i in 1..3) {
            delay(100)
            emit(i)
            println("Emitting $i ${Thread.currentThread().name} ")
        }
    }

    /**
     * 打印如下
     (1)未加buffered
    Collected 1 Test worker @coroutine#1
    Emitting 1 Test worker @coroutine#1
    Collected 2 Test worker @coroutine#1
    Emitting 2 Test worker @coroutine#1
    Collected 3 Test worker @coroutine#1
    Emitting 3 Test worker @coroutine#1
    Collected in 1289 ms
     (2)加了buffered
    Emitting 1 Test worker @coroutine#2
    Emitting 2 Test worker @coroutine#2
    Emitting 3 Test worker @coroutine#2
    Collected 1 Test worker @coroutine#1
    Collected 2 Test worker @coroutine#1
    Collected 3 Test worker @coroutine#1
    Collected in 1133 ms
     (3)加了flowOn
    Emitting 1 DefaultDispatcher-worker-1 @coroutine#2
    Emitting 2 DefaultDispatcher-worker-1 @coroutine#2
    Emitting 3 DefaultDispatcher-worker-1 @coroutine#2
    Collected 1 Test worker @coroutine#1
    Collected 2 Test worker @coroutine#1
    Collected 3 Test worker @coroutine#1
    Collected in 1208 ms
     (4)加了conflate
    Emitting 1 Test worker @coroutine#2
    Emitting 2 Test worker @coroutine#2
    Emitting 3 Test worker @coroutine#2
    Collected 1 Test worker @coroutine#1
    Collected 3 Test worker @coroutine#1
    Collected in 800 ms
     (5)加了collectLatest
    Emitting 1 Test worker @coroutine#2
    Emitting 2 Test worker @coroutine#2
    Emitting 3 Test worker @coroutine#2
    Collected 3 Test worker @coroutine#5
    Collected in 746 ms
     */
    @Test
    fun `test flow back pressure`() = runBlocking<Unit>{
        val time = measureTimeMillis {
            simpleFlow8()
                    //flowOn可以切换发射的线程
//                .flowOn(Dispatchers.Default)
                    //buffered可以让发射部分，并行发射
//                .buffer(50)
                    //conflate只会处理最新的值，而过滤掉中间的值
//                .conflate()
                    //只处理最新的值，甚至不去处理第一个值
                .collectLatest {   value->
//                .collect { value->
                delay(300)
                println("Collected $value ${Thread.currentThread().name} ")
            }
        }
        println("Collected in $time ms")

    }


    @Test
    fun `my flow test`() = runBlocking<Unit>{
        launch {
            flow {
                for(i in 1..3) {
                    println("$i emit")
                    emit(i)
                }
            }.filter {
                println("$it filter")
                it % 2 != 0
            }.map {
                println("$it map")
                "${it * it} money"
            }.collect {
                println("i get $it")
            }
        }
    }

    @Test
    fun `my flow test1`() = runBlocking<Unit>{
        launch {
            // 1. 生成一个 Channel
            val channel = produce<Int> {
                for(i in 1..5){
                    delay(200)
                    send(i * i)
                }
                close()
            }

            // 3. Channel 接收数据
            launch {
                for( y in channel)
                    println("get $y")
            }
        }
    }

}
















