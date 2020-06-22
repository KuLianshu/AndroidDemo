package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.BindingConversion;
import androidx.databinding.DataBindingUtil;

import com.example.databindingdemo.databinding.ActivityMain11Binding;

public class Main11Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain11Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main11);
    }

    //text 写成这样的形式才起作用(即@{String})：android:text='@{"hhh"}'
    //注意BindingConversion的优先级高于BindingAdapter
//    @BindingConversion
//    public static String conversionString(String text){
//        return text+"-conversionString";
//    }
}
