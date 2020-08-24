package com.example.ipcdemo.utils;

import android.os.Environment;

import java.io.File;

public class MyConstants {

    public static final String CHAPTER_2_PATH= Environment.getExternalStorageDirectory().getPath()+File.separator+"data";

    public static final String CHANGE_FILE_PATH= CHAPTER_2_PATH+File.separator+"myData.txt";

    public static final int MSG_FROM_CLIENT =1;

    public static final int MSG_FROM_SERVICE = 2;

    public static final String MSG_KEY = "msg";

    public static final String MSG_KEY1="replay";


}
