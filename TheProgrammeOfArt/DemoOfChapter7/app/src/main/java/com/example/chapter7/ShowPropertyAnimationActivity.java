package com.example.chapter7;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.FloatArrayEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.chapter7.widget.ViewWrapper;

import org.w3c.dom.Text;

public class ShowPropertyAnimationActivity extends Activity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_property_animation);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.text_view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showObjectAnimation();
                showAnimationSet();

//                showPropertyAnimation();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showValueAnimation();
//                showPropertyAnimation();

//                performAnimation();
                performAnimation(textView,textView.getWidth(),500);
            }
        });

    }

    private void showObjectAnimation(){
        ObjectAnimator.ofFloat(imageView,"translationY",100F).start();
    }

    private void showValueAnimation(){
        ValueAnimator colorAnim = ObjectAnimator.ofInt(textView,"backgroundColor",0xFFF8080,0xFF8080FF);
        colorAnim.setDuration(300);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }


    private void showAnimationSet(){

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(imageView, "rotationX", 0,360),
                ObjectAnimator.ofFloat(imageView, "rotationY", 0,180),
                ObjectAnimator.ofFloat(imageView, "rotation", -90),
                ObjectAnimator.ofFloat(imageView, "translationX", 0,90),
                ObjectAnimator.ofFloat(imageView, "translationY", 0,90),
                ObjectAnimator.ofFloat(imageView, "scaleX", 1,1.5f),
                ObjectAnimator.ofFloat(imageView, "scaleY", 1,0.5f),
                ObjectAnimator.ofFloat(imageView, "alpha", 1,0.25f,1)
        );
        set.setDuration(5*1000).start();

    }



    private void showPropertyAnimation(){
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.property_animator);
        ViewWrapper wrapper = new ViewWrapper(imageView);
        set.setTarget(wrapper);
        set.start();
    }

    private void performAnimation(){
        ViewWrapper wrapper = new ViewWrapper(textView);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(wrapper,"width",500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

    private void performAnimation(final View targetView, final int start, final int end){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1,end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator 对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整形，1~100之间
                int currentValue = (int) animator.getAnimatedValue();
                Log.i("WLY","current value: "+currentValue);

                //获得当前进度占整个动画过程的比例，浮点型，0~1之间
                float fraction = animator.getAnimatedFraction();
                //直接调用整型估值器，通过比例计算出宽度，然后再设给Button
                targetView.getLayoutParams().width = mEvaluator.evaluate(fraction,start,end);
                targetView.requestLayout();

            }
        });
        valueAnimator.setDuration(5000).start();
    }
}
