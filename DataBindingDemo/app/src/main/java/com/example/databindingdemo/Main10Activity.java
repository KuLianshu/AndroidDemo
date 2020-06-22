package com.example.databindingdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.databinding.DataBindingUtil;

import com.example.databindingdemo.databinding.ActivityMain10Binding;
import com.example.databindingdemo.model.Image;

import java.util.Random;

public class Main10Activity extends Activity {

    private Image image;

    private String path="H://hh/aa";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain10Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main10);
        image = new Image("",path);
        binding.setImage(image);

        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = path+new Random().nextInt(100);
                image.setPath(p);
            }
        });


    }

    @BindingAdapter("path")
    public static void loadImage(ImageView view, String path){
        //Image 类中path的get方法的返回值必须是ObservableField<String>，不然path的变化不会被监听到
        Log.i("WLY","loadImage path : "+path);
    }

    @BindingAdapter("android:text")
    public static void setTextColor(TextView textView, String text){
        int red = new Random().nextInt(254);
        int blue = new Random().nextInt(254);
        int green = new Random().nextInt(254);
        textView.setTextColor(Color.rgb(red,blue,green));
        //注意加上setText()，不然TextView不会显示text
        textView.setText(text);
    }


}
