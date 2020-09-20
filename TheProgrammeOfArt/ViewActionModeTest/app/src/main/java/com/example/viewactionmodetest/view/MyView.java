package com.example.viewactionmodetest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.viewactionmodetest.utils.LogUtils;

public class MyView extends View {
    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.i("View, dispatchTouchEvent");

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtils.i("View ACTION_DOWN");
                break;
//                return true;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("View ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.i("View ACTION_UP");
                break;

            case MotionEvent.ACTION_CANCEL:
                LogUtils.i("View ACTION_CANCEL");
                break;
        }

        LogUtils.i("View, onTouchEvent");


        return super.onTouchEvent(event);

    }


}
