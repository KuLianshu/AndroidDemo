package com.example.databindingdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.BindingConversion;
import androidx.databinding.DataBindingUtil;

import com.example.databindingdemo.databinding.ActivityMain12Binding;


public class Main12Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMain12Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_main12);

        }

    @BindingConversion
    public static Drawable convertStringToDrawable(String str){
        if (str.equals("红色")){
            return new ColorDrawable(Color.parseColor("#FF4081"));
        }
        if (str.equals("蓝色")){
            return new ColorDrawable(Color.parseColor("#3F51B5"));
        }
        return new ColorDrawable(Color.parseColor("#344567"));
    }

    @BindingConversion
    public static int convertStringToColor(String str){
        if (str.equals("红色")){
            return Color.parseColor("#FF4081");
        }
        if (str.equals("蓝色")){
            return Color.parseColor("#344567");
        }
        return Color.parseColor("#344567");
    }

}
