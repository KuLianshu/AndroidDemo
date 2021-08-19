package com.example.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.chapter4.widget.MyView;

public class MainActivity extends AppCompatActivity {

    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = findViewById(R.id.my_view);


    }

    /**
     * 获取View的宽高 方法1 Activity/View#onWindowFocusChanged
     * 注意：onWindowFocusChanged会被调用多次，
     * 当Activity的窗口得到焦点和失去焦点均会被调用一次。
     * 具体来说，当Activity继续执行和暂停执行时，
     * onWindowFocusChanged均会被调用。
     *
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus){
            measuredFunction(0);
            method3();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        method0();
        method1();

    }


    /**
     * 获取View的宽高 方法2 view.pot(runnable)
     */
    private void method0(){
        /*
         * 通过post可以将一个runnable投递到消息队列的尾部，
         * 然后等待Looper调用此runnable的时候，View也已经
         * 初始化好了。
         */
        myView.post(() -> measuredFunction(1));
    }

    /**
     * 获取View的宽高 方法3 ViewTreeObserver
     */
    private void method1(){
        ViewTreeObserver observer = myView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /*
            * 当View树的状态发生改变或者View树内部的View的可见性变化时，
            * 该方法会被调用。注意，伴随着View树的状态的改变等，该方法会被
            * 调用多次。
             */
            @Override
            public void onGlobalLayout() {
                //移除监听
                myView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                measuredFunction(2);
            }
        });
    }

    /**
     * 获取View的宽高 方法4 view.measure(int widthMeasureSpec, int heightMeasureSpec)
     * 通过手动对View进行measure来得到View的宽高，
     * 这种方法要根据View的LayoutParams来分
     */
    private void method3(){
        /*
        * (1)math_parent
        * 直接放弃，无法测量出具体宽高，因为无法得知父容器的宽高。
         */

        /*
        * (2)wrap_content
         */
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1<<30)-1, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec =View.MeasureSpec.makeMeasureSpec((1<<30)-1, View.MeasureSpec.AT_MOST);
        myView.measure(widthMeasureSpec,heightMeasureSpec);
        Log.i("WLY","widthMeasureSpec = "+widthMeasureSpec);
        Log.i("WLY","heightMeasureSpec = "+heightMeasureSpec);
    }

    /**
     * 获取View的宽高 错误的方法1
     * MeasureSpec 获取方式错误
     */
    private void method4(){
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(-1, View.MeasureSpec.UNSPECIFIED);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(-1, View.MeasureSpec.UNSPECIFIED);
//        myView.measure(widthMeasureSpec,heightMeasureSpec);
//        Log.i("WLY","widthMeasureSpec = "+widthMeasureSpec);
//        Log.i("WLY","heightMeasureSpec = "+heightMeasureSpec);
    }

    /**
     * 获取View的宽高 错误的方法2
     * 同样是MeasureSpec 获取方式错误
     */
    private void method5() {
        myView.measure(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
    }

        private void measuredFunction(int id){
        int measuredWidth = myView.getMeasuredWidth();
        int measuredHeight = myView.getMeasuredHeight();
        Log.i("WLY","measuredWidth"+id+ "= "+measuredWidth);
        Log.i("WLY","measuredHeight"+id+ "= "+measuredHeight);
    }


}