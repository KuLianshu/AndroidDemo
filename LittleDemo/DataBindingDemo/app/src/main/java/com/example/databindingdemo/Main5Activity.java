package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableList;
import androidx.databinding.ObservableMap;

import com.example.databindingdemo.databinding.ActivityMain5Binding;

import java.util.Random;

public class Main5Activity extends Activity {

    private ObservableMap<String,String> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain5Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main5);
        map = new ObservableArrayMap <>();
        map.put("name", "zhangsan");
        map.put("age", "25");
        binding.setMap(map);

        ObservableList<String> list = new ObservableArrayList<>();
        list.add("aa");
        list.add("bb");
        binding.setList(list);
        binding.setIndex(0);
        binding.setKey("name");


    }

    public void onClick(View view){
        map.put("name", "zhangsan, hi" + new Random().nextInt(100));
    }


}
