package com.example.chapter7.widget;

import android.view.View;

public class ViewWrapper {
    private View mTarget;

    public ViewWrapper(View target){
        mTarget = target;
    }

    public int getWidth(){
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width){
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }


    public int getHeight(){
        return mTarget.getLayoutParams().height;
    }

    public void setHeight(int height){
        mTarget.getLayoutParams().height = height;
        mTarget.requestLayout();
    }

}
