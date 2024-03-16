package com.example.coroutinescopeexceptiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handler = CoroutineExceptionHandler{_,exception->
            Log.e("WLY","Caught $exception")
        }
        findViewById<Button>(R.id.button).also {
            it.setOnClickListener {
//                GlobalScope.launch(handler) {
                //这里虽然没有添加异常处理器，但是异常会被全局处理器GlobalCoroutineExceptionHandler捕获
                GlobalScope.launch{
                    Log.i("WLY","on click .. ")
                    "abc".substring(10)
                }
            }
        }
    }
}