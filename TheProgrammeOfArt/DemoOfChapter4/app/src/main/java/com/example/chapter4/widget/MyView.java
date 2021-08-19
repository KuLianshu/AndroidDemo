package com.example.chapter4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.chapter4.R;

public class MyView extends View {

    private int mWidth = 200;
    private int mHeight = 200;

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
        setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.drawable_bg,null));

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新测量，保证在warp_content的情况下View也有固定大小
        measureWidthAndHeight(widthMeasureSpec,heightMeasureSpec);
        //不需要再调用父类的测量方法
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 直接继承View的自定义控件需要重写onMeasure方法
     * 并设置wrap_content时的自身大小，否则在布局中
     * 使用wrap_content就相当于使用match_parent。
     * 在onMeasure方法中调用此方法
     */

    private void measureWidthAndHeight(int widthMeasureSpec, int heightMeasureSpec){
        int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST){
            //设置宽度测量值
            setMeasuredDimension(mWidth,mHeight);
        }else if (widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth,heightSpecSize);
        }else if (heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,mHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void setWidthAndHeight(int width, int height){
        mWidth = width;
        mHeight = height;

    }





}
