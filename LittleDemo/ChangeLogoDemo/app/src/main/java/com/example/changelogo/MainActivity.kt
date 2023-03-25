package com.example.changelogo

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

/**
 * 这里展示了
 * 1、代码修改Logo和APP名称的方式
 * 2、获取Gradle文件中常量ASPECT_PORTRAIT的值
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        initData()


    }

    private fun initData() {

        val ASPECT_PORTRAIT: String = BuildConfig.ASPECT_PORTRAIT
        val data : Float = ASPECT_PORTRAIT.toFloat()
        Log.i("WLY","ASPECT_PORTRAIT: $ASPECT_PORTRAIT")
        Log.i("WLY","data: $data")
    }

    private fun initView() {
        findViewById<Button>(R.id.btn1).setOnClickListener {
            changeIcon("com.example.changelogo.A1")
        }

        findViewById<Button>(R.id.btn2).setOnClickListener {
            changeIcon("com.example.changelogo.A2")
        }

        findViewById<Button>(R.id.btn3).setOnClickListener {
            changeIcon("com.example.changelogo.A3")
        }

        findViewById<Button>(R.id.btn4).setOnClickListener {
            changeIcon("com.example.changelogo.MainActivity")
        }

    }


    /**
     * 动态修改Logo和APP名称
     */
    private fun changeIcon(name:String){
        val pm = packageManager
        pm.apply {
            setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP)

            setComponentEnabledSetting(ComponentName(this@MainActivity,name),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP)
        }
        restart(pm)
    }

    private fun restart(pm: PackageManager){
        val am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val intent = Intent(Intent.ACTION_MAIN)
        intent.apply {
            addCategory(Intent.CATEGORY_HOME)
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        val resolveIfoList = pm.queryIntentActivities(intent,0)
        for (resolveInfo in resolveIfoList){
            if (resolveInfo.activityInfo !=null){
                am.killBackgroundProcesses(resolveInfo.activityInfo.packageName)
            }
        }
    }


}