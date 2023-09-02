package com.example.lifecycle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifecycle.databinding.ActivityLifeCycleDemo1Binding
import com.example.lifecycle.service.MyLocationService

class LifeCycleDemoActivity1: AppCompatActivity(){

    private lateinit var binding: ActivityLifeCycleDemo1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLifeCycleDemo1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleBtnStart.setOnClickListener {
            startGPS()
        }

        binding.lifecycleBtnStop.setOnClickListener {
            stopGPS()

        }

    }

    private fun startGPS() {
        startService(Intent(this, MyLocationService::class.java))
    }

    private fun stopGPS() {
        stopService(Intent(this, MyLocationService::class.java))
    }


}