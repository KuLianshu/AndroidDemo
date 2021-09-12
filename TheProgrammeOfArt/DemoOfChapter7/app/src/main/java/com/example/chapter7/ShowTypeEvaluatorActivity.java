package com.example.chapter7;

import android.animation.ArgbEvaluator;
import android.animation.IntArrayEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.WidgetContainer;

import com.example.chapter7.widget.ViewWrapper;

/**
 * 2. 估值器（TypeEvaluator）
 * 2.1 简介
 * 定义：一个接口
 * 作用：设置 属性值 从初始值过渡到结束值 的变化具体数值
 * 插值器（Interpolator）决定 值 的变化规律（匀速、加速blabla），即决定的是变化趋势；而接下来的具体变化数值则交给
 * 而估值器属性动画特有的属性
 *
 */
public class ShowTypeEvaluatorActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_type_evaluator);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnimation1();
            }
        });

    }

    private void showAnimation1(){
        ViewWrapper wrapper = new ViewWrapper(imageView);
        ObjectAnimator anim = ObjectAnimator.ofObject(wrapper,"height",new IntEvaluator(),0,500);
        anim.start();
    }
}
