package com.example.viewactionmodetest.gesture;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.viewactionmodetest.R;
import com.example.viewactionmodetest.utils.LogUtils;

public class GestureDetectorActivity extends Activity {

    private GestureDetector mDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector_demo);

        iniGestureListener();
//        iniGestureListener1();
        Button button = findViewById(R.id.btn_activity_gesture_detector_demo);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //必须要获取 MotionEvent 对象才能监听手势
                //让某个View检测手势 - 重写View的onTouch函数，
                // 将View的触屏事件交给GestureDetector处理，从而对用户手势作出响应
                return mDetector.onTouchEvent(motionEvent);
            }
        });

        /**
         *
        *如果需要一个View既可以响应单击事件，又可以响应双击事件，最好使用SingleTapConfirmed结合OnDoubleTapListener的方式，
        而不是onClickListener 和OnDoubleTapListener的方式，因为第二种方法在双击的时候，onClickListener 还是会被调用的。
         */
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(GestureDetectorActivity.this,"on Click!",Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    /**
     * 子线程中创建 GestureDetector 的方法
     */
//    private Handler handler = new Handler();
    private void iniGestureListener1() {

        final GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(GestureDetectorActivity.this,"hello world",Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }
        };
        new Thread(){
            @Override
            public void run() {
                super.run();
                //方案一，传入一个保有 Looper 对象的 Hander
                mDetector = new GestureDetector(GestureDetectorActivity.this,listener,new Handler(Looper.getMainLooper()));

                //方案二，在主线程中创建 Handler ，这样就不用在创建 Handler 时，传入主线程的 Looper
//                mDetector = new GestureDetector(GestureDetectorActivity.this,listener,handler);

                //方案三，和上面几个方法一样，只不过在子线称里提前准备好 Lopper ，这样子线称就和主线程一样了
//                Looper.prepare();
//                mDetector = new GestureDetector(GestureDetectorActivity.this,listener);

            }
        }.start();
    }

    /**
     * 在主线程中创建 GestureDetector
     */
    private void iniGestureListener(){

        /*
         * SimpleOnGestureListener 包含了所有手势监听方法的空实现，所以使用起来更方便
         */
        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){

            /**
             * 属于双击的范畴在，第二次点击按下时发生的回调
             * 双击后执行一次，单击不执行
             *
             */
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                LogUtils.i("----onDoubleTap------");
//
//                Toast.makeText(GestureDetectorActivity.this,"double  click up!",Toast.LENGTH_SHORT).show();
//                return super.onDoubleTap(e);
//            }

            /**
             *
             * 属于双击的范畴在，第二次点击后，手指抬起离开了屏幕时发生的回调
             * 双击后执行两次，单击不执行
             */
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                LogUtils.i("----onDoubleTapEvent------");
//                Toast.makeText(GestureDetectorActivity.this, "double  click up!",Toast.LENGTH_SHORT).show();
//                return super.onDoubleTapEvent(e);
//            }


            /**
             * 快速双击时不执行
             *
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                LogUtils.i("----onSingleTapConfirmed------");
                Toast.makeText(GestureDetectorActivity.this,"single  click!",Toast.LENGTH_SHORT).show();

                return super.onSingleTapConfirmed(e);
            }

            /**
             * 快速双击时只执行一次
             * @param e
             * @return
             */
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                LogUtils.i("----onSingleTapConfirmed------");
//                return super.onSingleTapUp(e);
//            }


//            @Override
//            public void onLongPress(MotionEvent e) {
//
//                Toast.makeText(GestureDetectorActivity.this, "onLongPress!",Toast.LENGTH_SHORT).show();
//                // 后续工作
//                super.onLongPress(e);
//            }
        };

        mDetector = new GestureDetector(this,listener);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //让某个Activity检测手势：重写Activity的dispatchTouchEvent函数，
        // 将触屏事件交给GestureDetector处理，从而对用户手势作出响应
        mDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}
