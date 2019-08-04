package com.example.weili.servciedemo.utils;

import android.util.Log;

/**
 * @author : KuLianshu
 * @date : 2019/7/21
 * Desc :
 */

public class LogUtils {

    private static String TAG="WW";

    public static void i(String tag){
        Log.i(TAG,tag);
    }

    public static void debug(String tag){
        Log.d(TAG,tag);
    }

}
