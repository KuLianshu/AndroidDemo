package com.example.workmanager.work

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class DWorker(private val context: Context, private val workerParameters: WorkerParameters): Worker(context,workerParameters) {

    override fun doWork(): Result {
        Log.i("WLY","${this::class.java.simpleName}+doWork")

        return Result.success()

    }


}