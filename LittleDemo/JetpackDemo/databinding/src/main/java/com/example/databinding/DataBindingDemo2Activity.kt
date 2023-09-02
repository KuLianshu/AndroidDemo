package com.example.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.databinding.ActivityDataBinding2DemoBinding

class DataBindingDemo2Activity: AppCompatActivity() {

    private lateinit var binding: ActivityDataBinding2DemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding2DemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.netWorkImage = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2Fbca032b2-b92f-4061-8a13-bb9a5feb151f%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1692695812&t=a8e4202c1c0a12000ef5fb00ba2fcd34"
        binding.localImage = R.drawable.catcat
    }
}