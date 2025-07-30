package com.example.arouterdemo.application

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.arouterdemo.BuildConfig

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

}