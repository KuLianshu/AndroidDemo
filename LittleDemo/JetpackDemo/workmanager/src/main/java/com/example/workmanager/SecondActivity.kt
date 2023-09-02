package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.work.*
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.JSONPObject
import com.example.workmanager.databinding.ActivityMainBinding
import com.example.workmanager.work.*
import java.util.concurrent.TimeUnit

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            addWork()
        }


    }



    private fun addWork() {

        val aWork = OneTimeWorkRequest
            .Builder(AWorker::class.java)
            .build()

        val bWork = OneTimeWorkRequest
            .Builder(BWorker::class.java)
            .build()

        val cWork = OneTimeWorkRequest
            .Builder(CWorker::class.java)
            .build()

        val dWork = OneTimeWorkRequest
            .Builder(DWorker::class.java)
            .build()

        val eWork = OneTimeWorkRequest
            .Builder(EWorker::class.java)
            .build()

        val workContinuation1 = WorkManager.getInstance(this)
            .beginWith(aWork)
            .then(bWork)


        val workContinuation2 = WorkManager.getInstance(this)
            .beginWith(cWork)
            .then(dWork)

        val taskList = mutableListOf<WorkContinuation>()
        taskList.add(workContinuation1)
        taskList.add(workContinuation2)

        //任务组合
        WorkContinuation.combine(taskList)
            .then(eWork)
            .enqueue()

//        val workManager = WorkManager.getInstance(this)
//        //任务提交给WorkManager
//        workManager
//            .beginWith(aWork)
//            .then(bWork)
//            .enqueue()
    }
}