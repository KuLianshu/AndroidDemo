package com.example.mylibrary.utils;

import android.util.Log;

public class LogUtil {

    private static final String TAG = "WLY";


    public static void i(String TAG,String msg){
        Log.i(TAG,msg);
    }

    public static void i(String msg){
        Log.i(TAG,msg);
    }

    public static void d(String TAG,String msg){
        Log.d(TAG,msg);
    }

    public static void d(String msg){
        Log.d(TAG,msg);
    }

    public static void e(String TAG,String msg){
        Log.e(TAG,msg);
    }

    public static void e(String msg){
        Log.e(TAG,msg);
    }

}
