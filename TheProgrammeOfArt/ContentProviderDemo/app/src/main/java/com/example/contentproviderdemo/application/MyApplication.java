package com.example.contentproviderdemo.application;

import android.app.Application;

import com.example.contentproviderdemo.utils.LogUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        LogUtils.i("application start, process name: ");
    }
}
