package com.example.viewactionmodetest.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ScrollerView extends LinearLayout {
    public ScrollerView(Context context) {
        this(context,null);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ScrollerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

     public  void smoothScrollTo(int destX, int destY){

     }

}
