package com.example.commonlibrary.utils;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "SERVER";

    public static void i(String content){
        Log.i(TAG,content);
    }

    public static void i(String TAG, String content){
        Log.i(TAG,content);
    }

}
