package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.databindingdemo.databinding.ActivityMain9Binding;
import com.example.databindingdemo.model.User;

public class Main9Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain9Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main9);
        User user = new User("zhangsan","123456");
        binding.setUserInfo(user);
    }
}
