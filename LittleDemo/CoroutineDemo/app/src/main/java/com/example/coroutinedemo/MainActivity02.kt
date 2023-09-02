package com.example.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coroutinedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.*

class MainActivity02 : AppCompatActivity(),CoroutineScope by MainScope() {

    private lateinit var binding: ActivityMainBinding
//    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
//            mainScope.launch {
//                try {
//                    delay(10000)
//                }catch (e: Exception){
//                    Log.i("WLY","e: ${e.message}")
//                }
//            }
            launch {
                try {
                    delay(10000)
                }catch (e: Exception){
                    Log.i("WLY","e: ${e.message}")
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
//        mainScope.cancel()
        cancel()
    }

}
