package com.example.chapter8.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.chapter8.R;

import java.util.PrimitiveIterator;

public class MyService extends Service {

    private Button mFloatingButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        initWindowParams();

        initFloatingButton();
    }


    private void initWindowParams(){
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                0,0, PixelFormat.TRANSPARENT
        );
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 100;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //26及以上必须使用TYPE_APPLICATION_OVERLAY   @deprecated TYPE_PHONE
            //且需要申请权限
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
    }

    private void initFloatingButton() {
        mFloatingButton = new Button(this);
        mFloatingButton.setText("button");
        mFloatingButton.setTextColor(getResources().getColor(R.color.white));
        mFloatingButton.setBackgroundColor(getResources().getColor(R.color.color_FF9800));
        mWindowManager.addView(mFloatingButton,mLayoutParams);

    }
}
