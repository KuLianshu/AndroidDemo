package com.example.chapter7;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * 1. 插值器（Interpolator）
 * 1.1 简介
 * 定义：一个接口
 * 作用：设置 属性值 从初始值过渡到结束值的变化规律
 * 如匀速、加速 & 减速 等等
 * 即确定了 动画效果变化的模式，如匀速变化、加速变化 等等
 */
public class ShowInterpolatorActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_interpolator);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showAnimation1();
                showAnimation2();
            }
        });
    }

    private void showAnimation1(){
        Animation animation = AnimationUtils.loadAnimation(ShowInterpolatorActivity.this,R.anim.anim_show_interpolator);
        imageView.startAnimation(animation);
    }

    private void showAnimation2(){
        Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(3000);
        Interpolator interpolator = new OvershootInterpolator();
        animation.setInterpolator(interpolator);
        imageView.startAnimation(animation);
    }
}
