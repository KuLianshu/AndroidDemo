package com.example.viewactionmodetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    View child = new View(this);
    public boolean dispatchTouchEvent(MotionEvent ev){
        boolean consume = false;
        if (onInterceptTouchEvent(ev)){
            consume = onTouchEvent(ev);
        }else {
            consume = child.dispatchTouchEvent(ev);
        }
        return consume;
    }


    public boolean onInterceptTouchEvent(MotionEvent ev){
        return false;
    }

}