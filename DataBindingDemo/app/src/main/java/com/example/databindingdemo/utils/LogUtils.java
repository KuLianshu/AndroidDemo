package com.example.databindingdemo.utils;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "WLY";

    public static void i(String msg){
        Log.i(TAG,msg);
    }

    public static void d(String msg){
        Log.d(TAG,msg);
    }

    public static void e(String msg){
        Log.e(TAG,msg);
    }

}
