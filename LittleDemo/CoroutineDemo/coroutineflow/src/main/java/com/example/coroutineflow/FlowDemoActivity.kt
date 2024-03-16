package com.example.coroutineflow

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coroutineflow.databinding.ActivityFlowDemoBinding
import com.example.coroutineflow.model.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FlowDemoActivity: AppCompatActivity() {

    private lateinit var binding : ActivityFlowDemoBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowDemoBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            button.setOnClickListener {
                //即使页面退到后台，launch{}中的代码依然会继续执行
//                lifecycleScope.launch{
                //页面退到后台，launchWhenStarted{}中的代码不会继续执行，因为此时Activity的状态不是Started
                lifecycleScope.launchWhenStarted{
                    mainViewModel.timeFlow.collect{time->
                        textView.text = time.toString()
                        Log.d("WLY","Update time $time in UI")
                    }
                }
            }
        }


    }

}