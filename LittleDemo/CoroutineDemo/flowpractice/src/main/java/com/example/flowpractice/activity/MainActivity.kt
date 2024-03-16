package com.example.flowpractice.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flowpractice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)


    }


}