package com.example.coroutineflow

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coroutineflow.databinding.ActivityFlowDemoBinding
import com.example.coroutineflow.model.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FlowDemoActivity: AppCompatActivity() {

    private lateinit var binding : ActivityFlowDemoBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        test3()

    }

//    fun test1(){
//        binding.apply {
//            button.setOnClickListener {
//                //即使页面退到后台，launch{}中的代码依然会继续执行
////                lifecycleScope.launch{
//                //页面退到后台，launchWhenStarted{}中的代码不会继续执行，但会为程序保留旧数据，所以重新启动程序，time不会重置为0
////                lifecycleScope.launchWhenStarted{
//                lifecycleScope.launch{
//                    //页面退到后台，repeatOnLifecycle(Lifecycle.State.STARTED){}中的代码会被完全停止，所以程序重新回到前台，time会被重置为0
//                    repeatOnLifecycle(Lifecycle.State.STARTED){
//                        mainViewModel.timeFlow.collect{time->
//                            textView.text = time.toString()
//                            Log.d("WLY","Update time $time in UI")
//                        }
//                    }
//                }
//            }
//        }
//    }

//    fun test2() {
//        binding.apply {
//            button.setOnClickListener {
//                //开启定时器
//                mainViewModel.startImer()
//            }
//            lifecycleScope.launch{
//                repeatOnLifecycle(Lifecycle.State.STARTED){
//                    //监听StateFlow
//                    mainViewModel.stateFlow.collect{time->
//                        textView.text = time.toString()
//                    }
//                }
//            }
//        }
//    }

//    fun test3() {
//        lifecycleScope.launch{
//            //这里repeatOnLifecycle(Lifecycle.State.STARTED){}不能缺，否则程序退到后台，flow还是会继续执行
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                mainViewModel.stateFlow.collect{time->
//                    binding.textView.text = time.toString()
//                    Log.i("WLY","time: $time")
//                }
//            }
//        }
//    }


    fun test3() {
        binding.apply {
            button.setOnClickListener {
                mainViewModel.startLogin()
            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    //因为SharedFlow是非粘性的，所以横竖屏切换之后，loginFlow的值会重置为null
                    mainViewModel.loginFlow.collect{
                        if (it.isNotBlank()){
                            Toast.makeText(this@FlowDemoActivity,it,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}