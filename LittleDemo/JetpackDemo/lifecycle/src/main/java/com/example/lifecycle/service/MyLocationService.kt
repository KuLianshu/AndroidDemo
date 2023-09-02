package com.example.lifecycle.service

import android.util.Log
import androidx.lifecycle.LifecycleService
import com.example.lifecycle.observer.MyLocationObserver

class MyLocationService: LifecycleService() {

    private val TAG = "WLY"
    init {
        Log.i(TAG,"MyLocationService init ======")
        val observer = MyLocationObserver(this)
        lifecycle.addObserver(observer)
    }

}
