package com.example.myapplication.arouter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.alibaba.android.arouter.launcher.ARouter
import com.example.myapplication.R
import com.example.myapplication.bean.User


class ARouterTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arouter_test)
        findViewById<Button>(R.id.button).setOnClickListener {
            startTest()
//            startTest1()
        }
    }

    private fun startTest1(){
        // 跳转时路径必须与注解path完全匹配（包括斜杠）
        ARouter.getInstance().build(PATH1).navigation()
    }

    private fun startTest(){
        ARouter.getInstance()
            //写目标Activity的地址，地址要和接收方定义的地址保持一致
//            .build(Constants.PATH)
            .build(PATH1)
//            .build("/com/ARouterTest1Activity")
            //向接收方传递String值，key的名称要和接收方的全局变量名保持一致
            .withString("name","张三")
            //向接收方传递Int值，key的名称要和接收方的全局变量名保持一致
            .withLong("key",100L)
            //向接收方传递对象，类本身要实现序列化，User实现了Serializable接口，所以使用withSerializable()传值
            .withSerializable("user", User("李四",23))
            //为了实现startActivityForResult，这里传递一个requestCode=100
            .navigation(this,100)
    }

    //处理接收方传递回来的数据
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode ==200){
            data?.let {
                val id = it.getIntExtra("id",0)
                Log.i("WLY","id = $id")
            }
        }
    }

}