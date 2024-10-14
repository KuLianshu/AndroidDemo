package com.example.demoofchapter11

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoofchapter11.databinding.ActivityAsysncTaskDemoBinding
import java.text.SimpleDateFormat
import java.util.*

class AsyncTaskDemoActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding  = DataBindingUtil.setContentView<ActivityAsysncTaskDemoBinding>(this,R.layout.activity_asysnc_task_demo)
        binding.button.setOnClickListener {
//            MyAsyncTask("MyAsyncTask#1").execute("")
//            MyAsyncTask("MyAsyncTask#2").execute("")
//            MyAsyncTask("MyAsyncTask#3").execute("")
//            MyAsyncTask("MyAsyncTask#4").execute("")
//            MyAsyncTask("MyAsyncTask#5").execute("")

            //并行执行
            MyAsyncTask("MyAsyncTask#1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"")
            MyAsyncTask("MyAsyncTask#2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"")
            MyAsyncTask("MyAsyncTask#3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"")
            MyAsyncTask("MyAsyncTask#4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"")
            MyAsyncTask("MyAsyncTask#5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"")

        }

    }

    inner class MyAsyncTask(name: String) : AsyncTask<String,Int,String>(){
        var mName = "AsyncTask"
        init {
            mName = name
        }
        
        override fun doInBackground(vararg params: String?): String {
            Thread.sleep(3000)
            return mName
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val df = SimpleDateFormat("yyyy-MM-dd HH:MM:SS", Locale.getDefault())
            Log.i("WLY","$result execute finish at ${df.format(Date())}")
        }

    }


}