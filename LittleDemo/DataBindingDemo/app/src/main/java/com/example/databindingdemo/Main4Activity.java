package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.databindingdemo.databinding.ActivityMain4Binding;
import com.example.databindingdemo.model.ObservableGoods;

import java.util.Random;

public class Main4Activity extends Activity {

    private ObservableGoods goods;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain4Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main4);
        goods = new ObservableGoods("code",24.3f,"hi");
        binding.setGoods(goods);
        binding.setHandler(new ObservableGoodsHandler());


    }


    public class ObservableGoodsHandler{
        public void change(){
            goods.setName("code"+new Random().nextInt(100));
            goods.setPrice(new Random().nextInt(100));
            goods.setDetails("hi"+new Random().nextInt(100));
        }
    }

}
