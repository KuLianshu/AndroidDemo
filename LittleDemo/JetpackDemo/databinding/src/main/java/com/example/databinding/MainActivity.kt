package com.example.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.databinding.databinding.ActivityMainBinding
import com.example.databinding.entity.Idol
import com.example.databinding.listener.EventHandlerListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val idol = Idol("新垣结衣",3)
        binding.idol = idol
        setContentView(binding.root)

        binding.eventHandle = EventHandlerListener(this)
    }
}
