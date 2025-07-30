package com.example.test

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.utils.Constants
@Route(path = Constants.PATH_TEST_ACTIVITY)
class TestActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<Button>(R.id.button2).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            skipToTest1()
        }

    }

    private fun skipToTest1(){
        ARouter.getInstance()
            //写目标Activity的地址，地址要和接收方定义的地址保持一致
            .build(Constants.PATH_DEMO_ACTIVITY)
            .navigation(this,300)
    }

}