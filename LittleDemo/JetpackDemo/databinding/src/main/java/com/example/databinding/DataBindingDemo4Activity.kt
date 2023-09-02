package com.example.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.databinding.ActivityDataBinding4DemoBinding
import com.example.databinding.model.MyUserViewModel
import com.example.databinding.model.UserViewModel

class DataBindingDemo4Activity: AppCompatActivity() {

    private lateinit var binding: ActivityDataBinding4DemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding4DemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.userViewModel = MyUserViewModel()
    }
}