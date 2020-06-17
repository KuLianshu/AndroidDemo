package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.databindingdemo.databinding.ActivityMain7Binding;
import com.example.databindingdemo.model.User;

public class Main7Activity extends Activity {

    User user;

    ActivityMain7Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main7);
        user = new User("张三","123456");
        binding.setUserInfo(user);
        binding.setUserPresenter(new UserPresenter());

    }

    public class UserPresenter{
        public void onUserNameClick(User user){
            Toast.makeText(Main7Activity.this,"用户名："+user.getName(),Toast.LENGTH_SHORT).show();
        }

        public void afterTextChanged(Editable s){
            user.setName(s.toString());
            binding.setUserInfo(user);
        }

        public void afterUserPasswordChanged(Editable s){
            user.setPassword(s.toString());
            binding.setUserInfo(user);
        }
    }

}
