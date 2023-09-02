package com.example.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.livedata.databinding.ActivityMainBinding
import com.example.livedata.viewmodel.MyViewModel
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get()

        viewModel.getCurrentSecond()?.observe(
            this

        ){
            binding.textView.text = viewModel.getCurrentSecond()?.value.toString()

        }

        startTime()
    }

    private fun startTime() {
        Timer().schedule(object : TimerTask(){
            override fun run() {
                //异步线程用postValue()
                //UI线程用setValue()
                viewModel.getCurrentSecond()?.postValue(viewModel.getCurrentSecond()?.value?.plus(1))
            }

        },1000,1000)

    }
}