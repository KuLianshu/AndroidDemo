package com.example.coroutinechannel


import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger


class CoroutineTest03 {


    /**
     * 不安全的并发
     * 每次输出结果都不一样
     */
    @Test
    fun `test not safe concurrent`() = runBlocking<Unit> {
        var count = 0
        List(1000){
            GlobalScope.launch { count++ }
        }.joinAll()
        println(count)
    }

    /**
     * 安全的并发，加了锁
     * 输出 1000
     */
    @Test
    fun `test safe concurrent`() = runBlocking<Unit> {
        var count = AtomicInteger(0)
        List(1000){
            GlobalScope.launch { count.incrementAndGet() }
        }.joinAll()
        println(count)
    }

    /**
     * 打印
     * 1000
     */
    @Test
    fun `test safe concurrent tools`() = runBlocking<Unit> {
        var count = 0
        val mutex = Mutex()
        List(1000){
            GlobalScope.launch {
                mutex.withLock {
                    count++
                }
            }
        }.joinAll()
        println(count)
    }

    /**
     * 打印
    1000
     */
    @Test
    fun `test safe concurrent tools2`() = runBlocking<Unit> {
        var count = 0
        val semaphore = Semaphore(1)
        List(1000){
            GlobalScope.launch {
                semaphore.withPermit {
                    count++
                }
            }
        }.joinAll()
        println(count)
    }

    /**
     * 计算时不去访问协程外部数据
     * 打印
     * 1000
     */
    @Test
    fun `test avoid access outer variable`() = runBlocking<Unit> {
        var count = 0
        val result = count + List(1000){
            GlobalScope.async { 1 }
        }.map { it.await() }.sum()
        println(result)
    }

}
















