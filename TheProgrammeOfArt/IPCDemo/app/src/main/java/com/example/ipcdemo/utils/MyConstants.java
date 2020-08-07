package com.example.ipcdemo.utils;

import android.os.Environment;

import java.io.File;

public class MyConstants {

    public static final String CHAPTER_2_PATH= Environment.getExternalStorageDirectory().getPath()+File.separator+"data";

    public static final String CHANGE_FILE_PATH= CHAPTER_2_PATH+File.separator+"myData.txt";

}
