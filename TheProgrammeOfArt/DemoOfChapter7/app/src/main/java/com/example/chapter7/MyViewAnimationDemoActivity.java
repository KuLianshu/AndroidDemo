package com.example.chapter7;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.chapter7.animation.Rotate3dAnimation;


public class MyViewAnimationDemoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_animation_demo);

        ImageView imageView = findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rotate3dAnimation animation = new Rotate3dAnimation(0,180,200,200,50,true);
                animation.setDuration(5000);
                imageView.startAnimation(animation);
            }
        });
    }
}
