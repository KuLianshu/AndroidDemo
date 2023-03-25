package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.databindingdemo.databinding.ActivityMain8Binding;
import com.example.databindingdemo.model.User;

public class Main8Activity extends Activity {

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain8Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main8);
        user = new User("zhangsan","123456");
        binding.setUserInfo(user);
        binding.setHandler(new StringHandler());

    }

    public class StringHandler{
        public void onUserNameClick(User user){
            Toast.makeText(Main8Activity.this,"用户名："+user.getName(),Toast.LENGTH_SHORT).show();
        }
    }
}
