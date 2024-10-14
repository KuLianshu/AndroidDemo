package com.example.demoofchapter11

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoofchapter11.databinding.ActivityThreadPoolBinding
import java.util.concurrent.*

class ThreadPoolActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityThreadPoolBinding>(this,R.layout.activity_thread_pool)
        binding.button2.setOnClickListener {
//            test0()
//            test1()
//            test2()
//            test3()
            test4()
        }

    }

    //根据当前cpu的数量来创建线程池
    fun test0(){
        // 获取当前设备的CPU核心数
        val cpuCount = Runtime.getRuntime().availableProcessors()
        // 创建一个线程池，包含与CPU核心数相同的线程
        val executor: ExecutorService = Executors.newFixedThreadPool(cpuCount)
        // 定义一个异步任务
        val asyncTask = Runnable {
            // 在这里执行异步任务的代码
            Thread.sleep(3000)
            Log.i("WLY","---------------2")

        }
        // 将异步任务提交给线程池执行
        executor.submit(asyncTask)
        // 关闭线程池
        executor.shutdown()
        Log.i("WLY","---------------1")
    }

    //设置线程池最大线程数和空闲线程保持时间等
    private fun test1(){
        //获取当前设备的CPU核心数
        val cpuCount = Runtime.getRuntime().availableProcessors()
        //线程池中最大线程的数量
        val maximumPoolSize  = cpuCount * 2
        //当线程池中线程数大于核心线程数时，多余的空闲线程的存活时间，单位为秒
        val keepAliveTime = 10L
        //创建一个线程池，包含与CPU核心数相同的线程
        val executor = ThreadPoolExecutor(
            cpuCount,
            maximumPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            LinkedBlockingDeque()
        )
        //定义一个异步任务
        val asyncTask = Runnable{
            //执行异步任务
            Thread.sleep(10000)
            Log.i("WLY","-----------2")
        }
        //将异步任务提交给线程池执行
        executor.submit(asyncTask)
        //关闭线程池
        executor.shutdown()
        Log.i("WLY","-----------1")
    }

    //设置任务队列已满时的拒绝策略
    private fun test2(){
        val cpuCount = 1
        val maximumPoolSize  = cpuCount*2
        val keepAliveTime = 10L
        val taskQueue = ArrayBlockingQueue<Runnable>(1)
        //定义一个拒绝策略，当任务队列已满时，直接抛出异常
        val rejectedExecutionHandler = RejectedExecutionHandler { r, executor ->
            throw RejectedExecutionException("Task $r rejected from $executor")
        }
        val executor = ThreadPoolExecutor(
            cpuCount,
            maximumPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            taskQueue,
            rejectedExecutionHandler
        )

        try {
            //将异步任务提交给线程池执行
            for (i in 0..10){
                //定义一个异步任务
                val asyncTask = Runnable{
                    //执行异步任务
                    Thread.sleep(3000)
                    Log.i("WLY","task-----------$i")
                }
                executor.execute(asyncTask)
            }

        }catch (e: RejectedExecutionException){
            Log.e("WLY", "error: $e")
        }
        //关闭线程池
        executor.shutdown()
        Log.i("WLY","-----------start")
    }


    private fun test3(){
        val cpuCount = 1
        val maximumPoolSize  = 10
        val keepAliveTime = 10L
        val taskQueue = ArrayBlockingQueue<Runnable>(10)

        val executor = ThreadPoolExecutor(
            cpuCount,
            maximumPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            taskQueue
        )
        for (i in 0..5){
            val asyncTask = Runnable{
                Thread.sleep(1000)
                Log.i("WLY","task#$i current thread: ${Thread.currentThread().name}")
            }
            executor.execute(asyncTask)
        }
        executor.shutdown()
    }

    //WorkStealingPool示例
    fun test4() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val executorService = Executors.newWorkStealingPool()
            for (i in 0..9) {
                executorService.execute(SimpleTask(i))
            }
            executorService.shutdown()
            //虽说WorkStealingPool使用的是守护线程，但是这里是发生在应用程序不会退出的情况下的
            //所以SimpleTask都会打印，有没有awaitTermination无所谓
//            try {
//                executorService.awaitTermination(1, TimeUnit.HOURS)
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//                Thread.currentThread().interrupt()
//            }
        }

    }

    inner class SimpleTask(private val taskId: Int) : Runnable{
        override fun run() {
            Log.i("WLY","Task " + taskId + " executed by " + Thread.currentThread().name)
            // 模拟任务执行时间
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
                Thread.currentThread().interrupt()
            }
        }
    }



}