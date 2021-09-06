package com.example.chapter7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.chapter7.base.BaseActivity;

public class TransitionDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_demo);
        TextView textView = findViewById(R.id.start_activity);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(TransitionDemoActivity.this,ShowTransitionDemoActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);

                startActivity(ShowTransitionDemoActivity.class);

            }
        });
    }


}
