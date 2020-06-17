package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

import com.example.databindingdemo.databinding.ActivityMain3Binding;
import com.example.databindingdemo.model.Goods;
import com.example.databindingdemo.utils.LogUtils;

import java.util.Random;

/**
 * 单向绑定
 */
public class Main3Activity extends Activity {

    private Goods goods;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMain3Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main3);
        goods = new Goods("code","hi",24);
        binding.setGoods(goods);
        binding.setGoodsHandler(new GoodsHandler());
        goods.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == BR.name){
                    LogUtils.i("BR.name");
                }else if (propertyId == BR.details){
                    LogUtils.i("BR.details");
                }
            }
        });

    }

    public class GoodsHandler{
        public void changeGoodsName(){
            goods.setName("code"+new Random().nextInt(100));
            goods.setPrice(new Random().nextInt(100));
            goods.setDetails("hi"+new Random().nextInt(100));

        }

        public void changeGoodsDetails(){
            goods.setDetails("hi"+new Random().nextInt(100));
//            goods.setPrice(new Random().nextInt(100));
        }
    }

}
