package com.example.arouterdemo

import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.arouterdemo.fragment.FragmentActivity
import com.example.arouterdemo.provider.ContentProviderService
import com.example.arouterdemo.service.IRouteService
import com.example.common.bean.User
import com.example.common.utils.Constants


class MainActivity : AppCompatActivity() {


    private lateinit var receiver : BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver()
        initEvent()

    }

    private fun initEvent() {
        findViewById<Button>(R.id.button_outer_module).setOnClickListener {
            startTestOuterModule()
        }

        findViewById<Button>(R.id.button_inner_module).setOnClickListener {
            startTestInnerModule()
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            ARouter.getInstance()
                //写目标Activity的地址，地址要和接收方定义的地址保持一致
                .build(Constants.PATH_TEST_ACTIVITY)
                .navigation(this@MainActivity,100)
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            startActivity(Intent(this@MainActivity,FragmentActivity::class.java))
        }

        findViewById<Button>(R.id.button5).setOnClickListener {
            // 发送广播
            val intent = Intent(Constants.PATH_APP_BROADCAST_ACTION)
            sendBroadcast(intent)
        }

        findViewById<Button>(R.id.button6).setOnClickListener {
            startService()
        }
    }


    private fun startTestOuterModule(){
        ARouter.getInstance()
            //写目标Activity的地址，地址要和接收方定义的地址保持一致
            .build(Constants.PATH_OUTER_MODULE)
            //向接收方传递String值，key的名称要和接收方的全局变量名保持一致
            .withString("name","张三")
            //向接收方传递Int值，key的名称要和接收方的全局变量名保持一致
            .withLong("key",100L)
            //向接收方传递对象，类本身要实现序列化，User实现了Serializable接口，所以使用withSerializable()传值
            .withSerializable("user", User("李四",23))
            //为了实现startActivityForResult，这里传递一个requestCode=100
            .navigation(this@MainActivity,100)
    }


    private fun startTestInnerModule(){
        ARouter.getInstance()
            //写目标Activity的地址，地址要和接收方定义的地址保持一致
            .build(Constants.PATH_INNER_MODULE)
            //向接收方传递String值，key的名称要和接收方的全局变量名保持一致
            .withString("name","张三")
            //向接收方传递Int值，key的名称要和接收方的全局变量名保持一致
            .withLong("key",100L)
            //向接收方传递对象，类本身要实现序列化，User实现了Serializable接口，所以使用withSerializable()传值
            .withSerializable("user", User("李四",23))
            //为了实现startActivityForResult，这里传递一个requestCode=100
            .navigation(this@MainActivity,100)
    }

    //处理接收方传递回来的数据
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100){
            data?.let {
                val id = it.getIntExtra("id",0)
                Log.i("WLY","id = $id")
            }
        }
    }


    private fun registerReceiver(){
        // 注册广播
         receiver = ARouter.getInstance()
            .build(Constants.PATH_APP_BROADCAST)
            .navigation() as BroadcastReceiver
    }

    private fun getContentProvider(){
        val contentService = ARouter.getInstance()
            .build("/service/content")
            .navigation() as ContentProviderService

        val cursor = contentService.queryData()
        // 处理cursor数据...
    }

    private fun startService(){
        val service = ARouter.getInstance()
            .build("/service/upload")
            .navigation() as IRouteService
        service.startUploadService()
    }




}