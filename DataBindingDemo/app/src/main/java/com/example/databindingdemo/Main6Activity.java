package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.databindingdemo.databinding.ActivityMain6Binding;
import com.example.databindingdemo.model.ObservableGoods;

/**
 * 双向绑定
 * 双向绑定的意思即为当数据改变时同时使视图刷新，而视图改变时也可以同时改变数据
 */
public class Main6Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain6Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main6);
        ObservableGoods goods = new ObservableGoods("code", 23,"hi");
        binding.setGoods(goods);

    }

}
