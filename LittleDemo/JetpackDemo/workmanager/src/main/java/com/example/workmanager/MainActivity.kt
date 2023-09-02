package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.example.workmanager.databinding.ActivityMainBinding
import com.example.workmanager.work.MyWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            addWork()
        }

    }

//    @RequiresApi(Build.VERSION_CODES.O)
    private fun addWork() {

        val constraints = Constraints
            .Builder()
                //对网络没有要求
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()
        val data = Data.Builder()
            .putString("input_data","传入的数据")
            .build()
        val workRequest1 = OneTimeWorkRequest
            .Builder(MyWorker::class.java)
                //设置触发条件
            .setConstraints(constraints)
                //设置延迟执行
            .setInitialDelay(5, TimeUnit.SECONDS)
                //指数退避策略
//            .setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofSeconds(5))
                //设置tag标签
            .addTag("workRequest1")
                //设置参数
            .setInputData(data)
            .build()

        //周期性任务
        //WorkManager规定，周期性任务时间间隔不能少于15分钟
//        val workRequest2 = PeriodicWorkRequest.Builder(MyWork::class.java, Duration.ofMinutes(15))

        val workManager = WorkManager.getInstance(this)
        //任务提交给WorkManager
        workManager.enqueue(workRequest1)

    //观察任务状态
        workManager.getWorkInfoByIdLiveData(workRequest1.id).observe(this){workInfo ->
            Log.d("WLY", "workInfo: $workInfo")
            if (workInfo!=null&&workInfo.state == WorkInfo.State.SUCCEEDED){
                val outputData = workInfo.outputData.getString("output_data")
                Log.d("WLY", "outputData: $outputData")

            }
        }

//    //取消任务
//        Timer().schedule(object : TimerTask(){
//            override fun run() {
//                workManager.cancelWorkById(workRequest1.id)
//            }
//        },2000)


    }
}