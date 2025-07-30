package com.example.myapplication.mvvm.ui

import com.example.myapplication.mvvm.viewmodel.UserViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.myapplication.databinding.ActivityMvvmDemoBinding

import com.example.myapplication.mvvm.data.remote.NetworkModule
import com.example.myapplication.mvvm.data.repository.UserRepository

class MVVMDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmDemoBinding
    private val viewModel: UserViewModel by viewModels {
        UserViewModel.Factory(UserRepository(NetworkModule.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvvmDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnFetch.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            if (username.isNotEmpty()) {
                viewModel.fetchUser(username)
            }
        }
    }

    private fun setupObservers() {
        viewModel.user.observe(this) { user ->
            user?.let {
                binding.tvResult.text = "用户名: ${user.login}\nID: ${user.id}\n名称: ${user.name ?: "N/A"}\n简介: ${user.bio ?: "N/A"}"
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                binding.tvResult.text = "错误: $errorMessage"
            }
        }
    }
}    