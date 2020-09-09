package com.example.viewactionmodetest.utils;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "WLY";

    public static void i(String msg){
        i(TAG,msg);
    }

    public static void i(String TAG,String msg){
        Log.i(TAG, msg);
    }


}
