package com.example.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.coroutinedemo.databinding.ActivityMain03Binding
import com.example.coroutinedemo.databinding.ActivityMainBinding
import com.example.coroutinedemo.viewmodel.MainViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.*

class MainActivity03 : AppCompatActivity() {

    private lateinit var binding: ActivityMain03Binding
    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain03Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this


        binding.button.setOnClickListener {
            mainViewModel.getUser()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }

}
