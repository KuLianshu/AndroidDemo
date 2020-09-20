package com.example.viewactionmodetest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.viewactionmodetest.utils.LogUtils;

public class MyView1 extends View {
    public MyView1(Context context) {
        this(context,null);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.i("View1, dispatchTouchEvent");

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtils.i("View1 ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("View1 ACTION_MOVE");
                break;
//                return true;
            case MotionEvent.ACTION_UP:
                LogUtils.i("View1 ACTION_UP");
                break;

            case MotionEvent.ACTION_CANCEL:
                LogUtils.i("View1 ACTION_CANCEL");
                break;
        }

        LogUtils.i("View1, onTouchEvent");


        return super.onTouchEvent(event);

    }


}
