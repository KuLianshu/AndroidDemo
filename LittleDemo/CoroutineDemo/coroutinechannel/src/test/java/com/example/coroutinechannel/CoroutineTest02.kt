package com.example.coroutinechannel

import android.provider.Settings.Global
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.selects.select
import org.junit.Test


data class Response<T>(val value: T, val isLocal: Boolean)
data class User(val name: String)

fun CoroutineScope.getUserFromLocal(name: String) = async(Dispatchers.IO){
    delay(1000)
    User(name)
}

fun CoroutineScope.getUserFromRemote(name: String) = async(Dispatchers.IO){
    delay(2000)
    User(name)
}

class CoroutineTest02 {

    /**
     * 打印
    User(name=xxx)
     */
    @Test
    fun `test select await`() = runBlocking<Unit> {
        GlobalScope.launch{
            val localRequest = getUserFromLocal("xxx")
            val remoteRequest = getUserFromRemote("yyy")
            ///下面的请求，只会返回一个对象，最快的那个对象
            //因为getUserFromLocal()返回得更快，所以打印User(name=xxx)
            val userResponse = select<Response<User>> {
                localRequest.onAwait { Response(it, true) }
                remoteRequest.onAwait { Response(it, false) }
            }
            userResponse.value?.let { println(it) }
        }.join()
    }


    /**
     * 打印
    100
     */
    @Test
    fun `test select channel`() = runBlocking<Unit> {
        val channels = listOf(Channel<Int>(),Channel<Int>())
        GlobalScope.launch {
            delay(100)
            channels[0].send(200)
        }
        GlobalScope.launch {
            delay(50)
            channels[1].send(100)
        }
        val result = select<Int?> {
            channels.forEach{channel ->
                channel.onReceive{it}
            }
        }
        println(result)
    }

    /**
     * 打印
    job 2
    job 2 onJoin
    job 1
     */
    @Test
    fun `test SelectClause1`() = runBlocking<Unit> {
        val job1 = GlobalScope.launch {
            delay(100)
            println("job 1")
        }
        val job2 = GlobalScope.launch {
            delay(10)
            println("job 2")
        }

        select<Unit> {
            job1.onJoin{ println("job 1 onJoin") }
            job2.onJoin{ println("job 2 onJoin") }
        }
        delay(1000)
    }

    /**
     * 打印
    channels : [RendezvousChannel@4e07b95f{EmptyQueue}, RendezvousChannel@1f81aa00{EmptyQueue}]
    receive1: 200
    send on 1 RendezvousChannel@1f81aa00{EmptyQueue}
     */
    @Test
    fun `test SelectClause2`() = runBlocking<Unit> {
        val channels = listOf(Channel<Int>(),Channel<Int>())
        println("channels : $channels")
        launch(Dispatchers.IO) {
            select<Unit?> {
                launch {
                    delay(10)
                    channels[1].onSend(200){sendChannel ->
                        println("send on 1 $sendChannel")
                    }
                }

                launch {
                    delay(100)
                    channels[0].onSend(200){sendChannel ->
                        println("send on 0 $sendChannel")
                    }
                }
            }
        }

        GlobalScope.launch {
            println("receive0: ${channels[0].receive()}")
        }
        GlobalScope.launch {
            println("receive1: ${channels[1].receive()}")
        }
        delay(1000)
    }

    /**
     * 打印
    User(name=guest)
    User(name=guest)
     */
    @Test
    fun `test select flow`() = runBlocking<Unit> {
        //函数 -》协程 -》 Flow -》 Flow合并
        val name = "guest"
        coroutineScope {
            listOf(::getUserFromLocal,::getUserFromRemote)
                .map {function ->
                    function.call(name)
                }.map {deferred ->
                    flow { emit(deferred.await()) }
                }.merge().collect { user: User -> println(user) }
        }
    }

}
















