package com.example.databindingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.databindingdemo.databinding.ActivityMainBinding;
import com.example.databindingdemo.model.User;

public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        user = new User("张三","123456");
        binding.setUserInfo(user);
        binding.tvUserName.setText("法外狂徒");
    }
}
