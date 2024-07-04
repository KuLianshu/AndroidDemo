package com.wly.mvvm.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.wly.mvvm.R
import com.wly.mvvm.databinding.ActivityNewMvvmBinding
import com.wly.mvvm.viewmodel.NewTranslateViewModel

class NewMVVMActivity : AppCompatActivity(){

    private lateinit var binding: ActivityNewMvvmBinding
    private lateinit var mvvmViewModel: NewTranslateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_mvvm)

        initData()

        initEvent()

    }

    private fun initData(){

        mvvmViewModel = ViewModelProvider(this)[NewTranslateViewModel::class.java]


        //监听数据
        mvvmViewModel.getMTranslateData().observe(this){
            binding.translateBean = it
        }

        //监听数据
        mvvmViewModel.getLoadingLiveData().observe(this){aBoolean->
            //控制UI的逻辑在activity中，不要在xml中
            binding.progressBar.visibility = if (aBoolean) View.VISIBLE else View.GONE
        }

        //设置图片链接
        // 这里imageUrl不能为null，否则会报如下错误
        // java.lang.NullPointerException: Parameter specified as non-null is null: method com.wly.mvvm.adapter.CustomsBindingAdapter.setImage, parameter imageUrl
        //至少要初始化binding.imageUrl=""
        binding.imageUrl = "https://img.win3000.com/m00/d7/1a/d685398ab14eb827da7a883931cf38af.jpg"
        binding.defaultImageResource = R.mipmap.cat

    }

    private fun initEvent(){
        binding.tvClick.setOnClickListener {
            mvvmViewModel.requestTranslateData()

        }
    }



}
