package com.example.demoofchapter11

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoofchapter11.databinding.ActivityThreadPoolBinding
import java.lang.ref.WeakReference
import java.util.concurrent.*

class ThreadPoolDemoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityThreadPoolBinding
    private lateinit var threadPool: ExecutorService
    private  var scheduledPool: ScheduledExecutorService
    private var future1: Future<Int>? = null
    private var future2: Future<Int>? = null
    private var future3: Future<Int>? = null
    private var future4: Future<Int>? = null
    private var future5: Future<Int>? = null

    private var myHandler: MyHandler

    private var poolType = 0

    companion object{
        const val THREAD_UPDATE_1 = 0x100
        const val THREAD_UPDATE_2 = 0x101
        const val THREAD_UPDATE_3 = 0x102
        const val THREAD_UPDATE_4 = 0x103
        const val THREAD_UPDATE_5 = 0x104
    }

    init {
        scheduledPool = Executors.newScheduledThreadPool(4)
        myHandler = MyHandler(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_thread_pool)
        initView()
        initEvent()
    }

    private fun initView() {
        initToolBar()
        initProgress()
    }

    private fun initProgress() {
        binding.progressBar1.apply {
            progressBar.progress = 0
            progressBar.max = 100
            tvName.text = "线程一"
        }

        binding.progressBar2.apply {
            progressBar.progress = 0
            progressBar.max = 100
            tvName.text = "线程二"
        }

        binding.progressBar3.apply {
            progressBar.progress = 0
            progressBar.max = 100
            tvName.text = "线程三"
        }

        binding.progressBar4.apply {
            progressBar.progress = 0
            progressBar.max = 100
            tvName.text = "线程四"
        }

        binding.progressBar5.apply {
            progressBar.progress = 0
            progressBar.max = 100
            tvName.text = "线程五"
        }

    }

    private fun initToolBar() {
        binding.toolbar.title = "线程池"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun initEvent() {
        binding.tvReset.setOnClickListener {
            sendMsg(0, THREAD_UPDATE_1)
            sendMsg(0, THREAD_UPDATE_2)
            sendMsg(0, THREAD_UPDATE_3)
            sendMsg(0, THREAD_UPDATE_4)
            sendMsg(0, THREAD_UPDATE_5)
        }

        binding.tvThread1.setOnClickListener {
            startThread(future1, THREAD_UPDATE_1)
        }

        binding.tvThread2.setOnClickListener {
            startThread(future2, THREAD_UPDATE_2)
        }

        binding.tvThread3.setOnClickListener {
            startThread(future3, THREAD_UPDATE_3)
        }

        binding.tvThread4.setOnClickListener {
            startThread(future4, THREAD_UPDATE_4)
        }

        binding.tvThread5.setOnClickListener {
            startThread(future5, THREAD_UPDATE_5)
        }

        binding.tvThreadAll.setOnClickListener {
            startThread(future1, THREAD_UPDATE_1)
            startThread(future2, THREAD_UPDATE_2)
            startThread(future3, THREAD_UPDATE_3)
            startThread(future4, THREAD_UPDATE_4)
            startThread(future5, THREAD_UPDATE_5)
            when (poolType) {
                0 -> {
                    toast("当前线程池是ScheduledThreadPool,延迟2秒后执行")
                }
                1 -> {
                    toast("当前线程池是SingleThreadExecutor")
                }
                2 -> {
                    toast("当前线程池是CachedThreadPool")
                }
                3 -> {
                    toast("当前线程池是FixedThreadPool")
                }
            }
        }
        binding.tvStop1.setOnClickListener {
            stopThread(future1)
        }
        binding.tvStop2.setOnClickListener {
            stopThread(future2)
        }
        binding.tvStop3.setOnClickListener {
            stopThread(future3)
        }
        binding.tvStop4.setOnClickListener {
            stopThread(future4)
        }
        binding.tvStop5.setOnClickListener {
            stopThread(future5)
        }
        binding.tvStopAll.setOnClickListener {
            stopThread(future1)
            stopThread(future2)
            stopThread(future3)
            stopThread(future4)
            stopThread(future5)
        }
        binding.tvShutdown.setOnClickListener {
            if (!threadPool.isShutdown){
                threadPool.shutdown()
            }
        }
        binding.tvShutdownNow.setOnClickListener {
            if (!threadPool.isShutdown){
                threadPool.shutdownNow()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pool_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.item_pool_1 -> {
                poolType = 0
                scheduledPool = Executors.newScheduledThreadPool(4)
                binding.tvPoolType.text = "当前线程池类型：newScheduledThreadPool()"
            }
            R.id.item_pool_2 ->{
                poolType = 1
                threadPool = Executors.newSingleThreadExecutor()
                binding.tvPoolType.text = "当前线程池类型：newSingleThreadExecutor()"
            }
            R.id.item_pool_3 ->{
                poolType = 2
                threadPool = Executors.newCachedThreadPool()
                binding.tvPoolType.text = "当前线程池类型：newCachedThreadPool()"

                 }
            R.id.item_pool_4 ->{
                poolType = 3
                threadPool = Executors.newFixedThreadPool(4)
                binding.tvPoolType.text = "当前线程池类型：newFixedThreadPool(4)"
            }
        }
        return true
    }


    private fun startThread(future: Future<Int>?, what: Int){
        var f = future
        if (poolType == 0){
            if (f!=null&&!f.isDone||scheduledPool.isShutdown) return
        }else{
            if (f!=null&&!f.isDone||threadPool.isShutdown) return
        }
        val callable = object : Callable<Int>{
            var num = 0
            override fun call(): Int {
                while (num<100){
                    num++
                    sendMsg(num,what)
                    Thread.sleep(50)
                }
                return 100
            }
        }

        f = if (poolType == 0){
            scheduledPool.schedule(callable,2,TimeUnit.SECONDS)
        }else{
            threadPool.submit(callable)
        }

        when(what){
            THREAD_UPDATE_1 -> future1 = f
            THREAD_UPDATE_2 -> future2 = f
            THREAD_UPDATE_3 -> future3 = f
            THREAD_UPDATE_4 -> future4 = f
            THREAD_UPDATE_5 -> future5 = f
        }
    }

    private fun stopThread(future: Future<Int>?){
        if (poolType==0){
            if (future!=null&&!scheduledPool.isShutdown){
                future.cancel(true)
            }
        }else{
            if (future!=null&&!threadPool.isShutdown){
                future.cancel(true)
            }
        }
    }

    private fun sendMsg(num: Int, what: Int){
        val message = Message.obtain()
        message.what = what
        message.arg1 = num
        myHandler.sendMessage(message)
    }

    private inner class MyHandler(context: Context) : Handler(){

        private val activity: WeakReference<Context>
        init {
            activity = WeakReference(context)
        }
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (activity.get() == null) return
            when(msg.what){
                THREAD_UPDATE_1->{
                    binding.progressBar1.tvProgress.text = "${msg.arg1} %"
                    binding.progressBar1.progressBar.progress = msg.arg1
                }
                THREAD_UPDATE_2->{
                    binding.progressBar2.tvProgress.text = "${msg.arg1} %"
                    binding.progressBar2.progressBar.progress = msg.arg1
                }
                THREAD_UPDATE_3->{
                    binding.progressBar3.tvProgress.text = "${msg.arg1} %"
                    binding.progressBar3.progressBar.progress = msg.arg1
                }
                THREAD_UPDATE_4->{
                    binding.progressBar4.tvProgress.text = "${msg.arg1} %"
                    binding.progressBar4.progressBar.progress = msg.arg1
                }
                THREAD_UPDATE_5->{
                    binding.progressBar5.tvProgress.text = "${msg.arg1} %"
                    binding.progressBar5.progressBar.progress = msg.arg1
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myHandler.removeCallbacksAndMessages(null)
        if (!threadPool.isShutdown){
            threadPool.shutdown()
        }
        if (!scheduledPool.isShutdown){
            scheduledPool.shutdownNow()
        }
    }

    private fun toast(str: String){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
    }

}