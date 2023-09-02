package com.example.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lifecycle.databinding.ActivityLifeCycleDemoBinding

class LifeCycleDemoActivity: AppCompatActivity(){

    private lateinit var binding: ActivityLifeCycleDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_life_cycle_demo)
        lifecycle.addObserver(binding.chronometer)

    }




}