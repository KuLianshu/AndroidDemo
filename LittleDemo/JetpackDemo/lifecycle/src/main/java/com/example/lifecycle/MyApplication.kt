package com.example.lifecycle

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.lifecycle.observer.ApplicationObserver

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        //ProcessLifecycleOwner 针对整个应用程序的监听，与Activity无关
        //ProcessLifecycleOwner 中 Lifecycle.Event.ON_CREATE 只调用一次
        //Lifecycle.Event.ON_DESTROY 永远不会被调用
        ProcessLifecycleOwner.get().lifecycle
            .addObserver(ApplicationObserver())
    }

}