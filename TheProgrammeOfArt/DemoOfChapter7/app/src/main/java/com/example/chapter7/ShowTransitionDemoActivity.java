package com.example.chapter7;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.chapter7.base.BaseActivity;

public class ShowTransitionDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_transition_demo);
        TextView textView = findViewById(R.id.exit_activity);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
//    }
}
