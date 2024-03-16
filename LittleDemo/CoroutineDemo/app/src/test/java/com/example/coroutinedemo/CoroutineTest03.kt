package com.example.coroutinedemo

import android.util.Log
import kotlinx.coroutines.*
import org.junit.Test
import java.io.BufferedReader
import java.io.FileReader
import kotlin.system.measureTimeMillis

class CoroutineTest03 {

    @Test
    fun `test release resources`() = runBlocking<Unit> {

        val job = launch() {
            try {
                repeat(1000){i->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            }finally {
                println("job: I'm running finally")
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun `test use function`() = runBlocking<Unit> {
//        val br = BufferedReader(FileReader("D:\\log.txt"))
//        with(br){
//            var line: String?
//            try {
//                while (true){
//                    line = readLine()?:break
//                    println(line)
//                }
//            }finally {
//                close()
//            }
//
//        }
        BufferedReader(FileReader("D:\\log.txt")).use {
            //在use函数里不用写close
            var line: String?
            while (true){
                line = readLine()?:break
                println(line)
            }
        }
    }

    @Test
    fun `test cancel with NonCancellable`() = runBlocking<Unit> {
        val job = launch() {
            try {
                repeat(1000){i->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            }finally {
                println("job: I'm running finally")
                //这里是不会被挂起的
//                delay(1000L)
//                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                //这样才能挂起
                withContext(NonCancellable){
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")

    }


}