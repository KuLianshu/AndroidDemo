package com.example.databinding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.databinding.databinding.ActivityDataBinding6DemoBinding
import com.example.databinding.model.ScoreViewModel

class DataBindingDemo6Activity: AppCompatActivity() {

    private lateinit var binding: ActivityDataBinding6DemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding6DemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ScoreViewModel::class.java]

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

    }
}