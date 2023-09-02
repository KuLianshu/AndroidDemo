package com.example.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.livedata.databinding.ActivityLiveDataDemoBinding

class LiveDataDemoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLiveDataDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveDataDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}