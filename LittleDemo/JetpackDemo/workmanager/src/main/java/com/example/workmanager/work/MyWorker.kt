package com.example.workmanager.work

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(private val context: Context, private val workerParameters: WorkerParameters): Worker(context,workerParameters) {


    override fun doWork(): Result {
//        SystemClock.sleep(2000)

        val inputData = inputData.getString("input_data")
        Log.i("WLY","MyWork doWork, inputData : $inputData")

        val outputData = Data.Builder()
            .putString("output_data","执行成功")
            .build()

        return Result.success(outputData)
        //无参数返回时用下面的
//        return Result.success(outputData)
        //这里为retry()时，可以设置下退避策略setBackoffCriteria
//        return Result.retry()
    }


}