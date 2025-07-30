package com.example.myapplication.coroutine

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCoroutineActivityBinding
import kotlinx.coroutines.*

class CoroutineActivity: AppCompatActivity() {

    lateinit var binding : ActivityCoroutineActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutine_activity)

        test()
        binding.textView.setOnClickListener {
            Log.i("WLY","--------- CLICK -------")
        }
    }

    fun test(){
//        threadTest()
        coroutineTest()
    }

    fun threadTest(){
        count2()
    }

    fun count2(): Int{
        Log.i("WLY","--------- SLEEP START -------")
        Thread.sleep(5000)
        Log.i("WLY","--------- SLEEP END -------")
        return 5
    }

    fun coroutineTest(){
        val scope = MainScope()
        scope.launch {
            val count = async(Dispatchers.IO) { count1() }.await()
            binding.textView.text = "$count"
        }
    }

    suspend fun count1(): Int{
        Log.i("WLY","--------- COROUTINE START -------")
        delay(5000)
        Log.i("WLY","--------- COROUTINE END -------")
        return 5
    }


}