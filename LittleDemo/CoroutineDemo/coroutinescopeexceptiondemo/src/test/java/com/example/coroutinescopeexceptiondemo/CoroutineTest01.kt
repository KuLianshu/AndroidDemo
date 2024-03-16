package com.example.coroutinescopeexceptiondemo

import kotlinx.coroutines.*
import org.junit.Test

class CoroutineTest01 {

    @Test
    fun `test deal with timeout`() = runBlocking<Unit> {
        withTimeout(1300) {
            repeat(1000){i->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        }

    }

    @Test
    fun `test deal with timeout return null`() = runBlocking<Unit> {
        val result = withTimeoutOrNull(1300) {
            repeat(1000){i->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
            "Down"
        }?:"Jack"
        println("Result is $result")
    }




}