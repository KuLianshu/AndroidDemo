package com.example.databindingdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.example.databindingdemo.fragment.MyFragment;

public class Main1Activity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this,R.layout.activity_main1);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout,new MyFragment())
                .commit();
    }
}
