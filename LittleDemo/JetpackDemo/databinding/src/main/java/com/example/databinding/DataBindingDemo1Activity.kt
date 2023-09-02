package com.example.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.databinding.ActivityDataBinding1DemoBinding
import com.example.databinding.entity.Idol
import com.example.databinding.listener.EventHandlerListener

class DataBindingDemo1Activity: AppCompatActivity() {

    private lateinit var binding: ActivityDataBinding1DemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding1DemoBinding.inflate(layoutInflater)
        val idol = Idol("新垣结衣",3)
        binding.idol = idol
        setContentView(binding.root)

        binding.eventHandle = EventHandlerListener(this)
    }
}