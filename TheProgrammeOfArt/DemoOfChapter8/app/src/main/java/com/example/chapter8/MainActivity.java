package com.example.chapter8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.example.chapter8.service.MyService;

public class MainActivity extends AppCompatActivity {

    private final int ADD_VIEW_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("com.example.chapter8","com.example.chapter8.service.MyService"));


        if (!isGranted()){
            requestPermission();
        }else {
            startAddView();
        }


    }

    /**
     * 判断是否获取权限
     */
    private boolean isGranted(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Settings.canDrawOverlays(this);
        }
        return true;
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.getPackageName()));
            startActivityForResult(intent, ADD_VIEW_CODE);
        }

    }

    /**
     * 查看授权结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_VIEW_CODE){
            if (!isGranted()) {
                Toast.makeText(this, "权限被拒绝了", Toast.LENGTH_SHORT).show();
            }else {
                startAddView();
            }
        }
    }

    /**
     * 添加View
     */
    private void startAddView(){
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
        finish();
    }
}