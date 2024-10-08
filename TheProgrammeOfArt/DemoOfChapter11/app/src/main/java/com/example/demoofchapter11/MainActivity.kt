package com.example.demoofchapter11

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {



    init {
        //系统预置4种线程池的常用方法
        val command = Runnable {
            SystemClock.sleep(2000)
        }
        
        val fixedThreadPool = Executors.newFixedThreadPool(4)
        fixedThreadPool.execute(command)
        
        val cachedThreadPool = Executors.newCachedThreadPool()
        cachedThreadPool.execute(command)
        
        val scheduledThreadPool = Executors.newScheduledThreadPool(4)
        //2000ms后执行command
        scheduledThreadPool.schedule(command,2000, TimeUnit.MILLISECONDS)
        //延迟10ms后，每隔1000ms执行一次command
        scheduledThreadPool.scheduleAtFixedRate(command,10,1000,TimeUnit.MILLISECONDS)
        
        val singleThreadExecutor = Executors.newSingleThreadExecutor()
        singleThreadExecutor.execute(command)
        
    }
    
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val command = Runnable {
            try {
                for (i in 0..10){
                    Thread.sleep(2000)
                    Log.i("WLY","${Thread.currentThread().name}, Download $i ...")
                }


            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        val future = FutureTask {
            try {
                Log.i("WLY","${Thread.currentThread().name},START  ...")
//                Thread.sleep(3000)
                for (i in 0..10){
                    Thread.sleep(2000)
                    Log.i("WLY","${Thread.currentThread().name}, Download $i ...")
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            "finish"
        }

        val fixedThreadPool = Executors.newFixedThreadPool(4)
        fixedThreadPool.execute(command)
//        fixedThreadPool.execute(future)
        fixedThreadPool.shutdown()
        findViewById<TextView>(R.id.textView).setOnClickListener {
           Toast.makeText(this@MainActivity,"hhh",Toast.LENGTH_SHORT).show()
        }
        //这个方法卡主线程
        val result = future.get()
        Log.i("WLY","result : $result")
    }
    

}