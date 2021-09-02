package com.example.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.imageView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setViewAnimationTest();
//                setAlphaAnimationTest();

                setFrameAnimationTest();
            }
        });


    }
    
    private void setFrameAnimationTest(){
        imageView.setBackgroundResource(R.drawable.frame_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();

    }

    private void setAlphaAnimationTest(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(3000);
        textView.startAnimation(alphaAnimation);
    }

    private void setViewAnimationTest() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.view_animation_test);
        textView.startAnimation(animation);
    }
}