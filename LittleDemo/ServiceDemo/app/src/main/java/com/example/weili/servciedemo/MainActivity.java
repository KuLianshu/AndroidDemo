package com.example.weili.servciedemo;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.weili.servciedemo.service.MyService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_start_service_main_activity)
    Button mBtnStartService;
    @BindView(R.id.btn_stop_service_main_activity)
    Button mBtnStopService;
    @BindView(R.id.btn_bind_service_main_activity)
    Button mBtnBindService;
    @BindView(R.id.btn_unbind_service_main_activity)
    Button mBtnUnbindService;
    @BindView(R.id.btn_start_foreground_service_main_activity)
    Button mBtnStartForegroundService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.DownloadBinder binder = (MyService.DownloadBinder) service;
            binder.startDownload();
            binder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mBtnStartService.setOnClickListener(this);
        mBtnStopService.setOnClickListener(this);
        mBtnBindService.setOnClickListener(this);
        mBtnUnbindService.setOnClickListener(this);
        mBtnStartForegroundService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service_main_activity:
                startService(new Intent(this, MyService.class));
                break;
            case R.id.btn_stop_service_main_activity:
                stopService(new Intent(this, MyService.class));
                break;
            case R.id.btn_bind_service_main_activity:
                bindService(new Intent(this, MyService.class), connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service_main_activity:
                unbindService(connection);
                break;
            case R.id.btn_start_foreground_service_main_activity:


                break;
            default:
        }
    }
}
