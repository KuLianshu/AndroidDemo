package com.example.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.databinding.ActivityDataBindingDemoBinding
import com.example.databinding.entity.Idol
import com.example.databinding.listener.EventHandlerListener

class DataBindingDemoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDataBindingDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBindingDemoBinding.inflate(layoutInflater)
        val idol = Idol("新垣结衣",3)
        binding.idol = idol
        setContentView(binding.root)

        binding.eventHandle = EventHandlerListener(this)
    }
}