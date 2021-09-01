package com.example.chapter6;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionDrawable drawable = (TransitionDrawable) textView.getBackground();
                drawable.startTransition(2000);
            }
        });


        View view = findViewById(R.id.image);
        ScaleDrawable scaleDrawable = (ScaleDrawable) view.getBackground();
        scaleDrawable.setLevel(1);

        ImageView imageView = findViewById(R.id.test_clip);
        ClipDrawable clipDrawable = (ClipDrawable) imageView.getDrawable();
        clipDrawable.setLevel(6000);


    }
}