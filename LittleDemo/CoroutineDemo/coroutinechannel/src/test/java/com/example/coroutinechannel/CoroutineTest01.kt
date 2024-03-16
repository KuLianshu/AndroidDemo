package com.example.coroutinechannel

import android.provider.Settings.Global
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import org.junit.Test

class CoroutineTest01 {

    /**
     * 打印如下
    send 1
    receive 1
    send 2
    receive 2
    send 3
    receive 3
     */
    @Test
    fun `test know channel`() = runBlocking<Unit> {
        val channel = Channel<Int>()
        //生产者
        val producer = GlobalScope.launch {
            var i =0
            while (true){
                delay(1000)
                channel.send(++i)
                println("send $i")
            }
        }

        //消费者
        val consumer = GlobalScope.launch {
            while (true){
                val element = channel.receive()
                println("receive $element")
            }
        }

        joinAll(producer,consumer)
    }

    /**
     * 打印如下，可以看到，生产和消费成对出现，即使消费端delay时间更长，生产端会等待。
    receive 1
    send 1
    receive 2
    send 2
    receive 3
    send 3
     */
    @Test
    fun `test know channel2`() = runBlocking<Unit> {
        val channel = Channel<Int>()
        //生产者
        val producer = GlobalScope.launch {
            var i =0
            while (true){
                delay(1000)
                //缓冲区满了，没有人消费，send就会被挂起
                channel.send(++i)
                println("send $i")
            }
        }

        //消费者
        val consumer = GlobalScope.launch {
            while (true){
                //生产效率大于消费效率
                delay(2000)
                val element = channel.receive()
                println("receive $element")
            }
        }

        joinAll(producer,consumer)
    }


    /**
     *打印
    send1
    send4
    send9
    send16
    send25
    receive 1
    receive 4
    receive 9
    receive 16
    receive 25
     */
    @Test
    fun `test iterate channel`() = runBlocking<Unit> {
        val channel = Channel<Int>(Channel.UNLIMITED)
        //生产者
        val producer = GlobalScope.launch {
            for (x in 1..5){
                channel.send(x*x)
                println("send${(x*x)}")
            }
        }

        //消费者
        val consumer = GlobalScope.launch {
//            val iterator = channel.iterator()
//            while (iterator.hasNext()){
//                val element = iterator.next()
//                println("receive $element")
//                delay(2000)
//            }

            for(element in channel){
                println("receive $element")
                delay(2000)
            }
        }
        joinAll(producer,consumer)
    }

    /**
     * 打印
    receive: 0
    receive: 1
    receive: 2
    receive: 3
     */
    @Test
    fun `test fast producer channel`() = runBlocking<Unit> {
        val receiveChannel: ReceiveChannel<Int> =GlobalScope.produce {
            repeat(100){
                delay(1000)
                //执行100次发送
                send(it)
            }
        }

        val consumer = GlobalScope.launch {
            for (i in receiveChannel){
                println("receive: $i")
            }
        }
        consumer.join()
    }

    /**
     *打印
    0
    1
    2
    3
     */
    @Test
    fun `test fast consumer channel`() = runBlocking<Unit> {
        val sendChanel: SendChannel<Int> = GlobalScope.actor {
            while (true){
                val element = receive()
                println(element)
            }
        }
        val producer = GlobalScope.launch {
            for (i in 0..3){
                sendChanel.send(i)
            }
        }
        producer.join()
    }

    /**
     * 打印
    send 0
    send 1
    send 2
    receive 0
    close channel.|  -  ClosedForSend: true  -  ClosedForSend: false
    receive 1
    receive 2
    After Consuming.|  -  ClosedForSend: true  -  ClosedForSend: true
     */
    @Test
    fun `test close channel`() = runBlocking<Unit> {
        val channel = Channel<Int>(3)
        //生产者
        val producer = GlobalScope.launch {
            List(3){
                channel.send(it)
                println("send $it")
            }
            channel.close()
            println("close channel." +
                    "|  -  ClosedForSend: ${channel.isClosedForSend}"+
                    "|  -  ClosedForSend: ${channel.isClosedForReceive}".trimMargin())
        }
        //消费者
        val consumer = GlobalScope.launch {
            for (element in channel){
                println("receive $element")
                delay(1000)
            }
            println("After Consuming." +
                    "|  -  ClosedForSend: ${channel.isClosedForSend}"+
                    "|  -  ClosedForSend: ${channel.isClosedForReceive}".trimMargin())
        }
        joinAll(producer,consumer)
    }


    /**
     打印
     [#0 received: 0
     [#2 received: 0
     [#1 received: 0
     [#2 received: 1
     [#1 received: 1
     [#0 received: 1
     [#2 received: 2
     [#1 received: 2
     [#0 received: 2
     */
    @Test
    fun `test broadcast  channel`() = runBlocking<Unit> {
        val channel = Channel<Int>()
        //BroadcastChannel有两种声明方式
//        val broadcastChannel = channel.broadcast(3)
        //必须用BUFFERED
        val broadcastChannel = BroadcastChannel<Int>(Channel.BUFFERED)
        val producer =  GlobalScope.launch {
            List(3){
                delay(100)
                broadcastChannel.send(it)
            }
            broadcastChannel.close()
        }

        List(3){index->
            GlobalScope.launch {
                //接收者需要订阅
                val receiveChannel = broadcastChannel.openSubscription()
                for (i in receiveChannel){
                    println("[#$index received: $i")
                }
            }
        }.joinAll()
    }

}
















