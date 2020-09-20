package com.example.viewactionmodetest.touch;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.viewactionmodetest.R;
import com.example.viewactionmodetest.utils.LogUtils;
import com.example.viewactionmodetest.view.MyView;

public class TouchDemoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_demo);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.i("Activity, dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtils.i("Activity ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("Activity ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.i("Activity ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtils.i("Activity ACTION_CANCEL");
                break;
        }

        LogUtils.i("Activity, onTouchEvent");

        return super.onTouchEvent(event);
    }
}
