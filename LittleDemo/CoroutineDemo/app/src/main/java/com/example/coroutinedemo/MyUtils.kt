package com.example.coroutinedemo

import android.util.Log
import kotlinx.coroutines.*

class MyUtils {

   companion object{

       fun test(){
             runBlocking {
                launch(Dispatchers.IO) {
                    delay(20000)
                    Log.i("WLY","from launch ...")
                }
                delay(30000)
                Log.i("WLY","from coroutineScope ...")

            }
       }
   }


}