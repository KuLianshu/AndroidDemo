package com.example.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.databinding.ActivityDataBinding3DemoBinding
import com.example.databinding.model.UserViewModel

class DataBindingDemo3Activity: AppCompatActivity() {

    private lateinit var binding: ActivityDataBinding3DemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding3DemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.userViewModel = UserViewModel()
    }
}