package com.example.ipcdemo.utils;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "WLY";

    public static void i(String content){
        i(TAG,content);
    }

    public static void i(String TAG, String content){
        Log.i(TAG,content);
    }

    public static void d(String content){
        d(TAG,content);
    }

    public static void d(String TAG,String content){
        Log.d(TAG,content);
    }

    public static void e(String content){
        e(TAG,content);
    }

    public static void e(String TAG,String content){
        Log.e(TAG,content);
    }

}
