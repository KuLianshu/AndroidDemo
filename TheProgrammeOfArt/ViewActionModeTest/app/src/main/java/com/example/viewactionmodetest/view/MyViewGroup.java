package com.example.viewactionmodetest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.viewactionmodetest.utils.LogUtils;

public class MyViewGroup extends RelativeLayout {

    public MyViewGroup(Context context) {
        this(context,null);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.i("ViewGroup onClick");
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        LogUtils.i("ViewGroup, dispatchTouchEvent");

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.i("ViewGroup, onInterceptTouchEvent");

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtils.i("ViewGroup ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("ViewGroup ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.i("ViewGroup ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtils.i("ViewGroup ACTION_CANCEL");
                break;
        }

        LogUtils.i("ViewGroup, onTouchEvent");

        return super.onTouchEvent(event);
    }



}
