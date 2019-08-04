package com.example.weili.servciedemo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.weili.servciedemo.MainActivity;
import com.example.weili.servciedemo.utils.LogUtils;

/**
 * @author : KuLianshu
 * @date : 2019/7/21
 * Desc :
 */

public class MyService extends Service {

    private DownloadBinder mBinder=new DownloadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.i("myService onBind------------");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i("myService onCreate-------");

        Intent intent=new Intent(this, MainActivity.class);
        PendingIntent pi=PendingIntent.getActivities(this,0,new Intent[]{intent},0);
//        Notification notification=new NotificationCompat.Builder(this)

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("myService onStartCommand-------");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.i("myService onUnbind------------");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("myService onDestroy-------");
    }

    public class DownloadBinder extends Binder{
        public void startDownload(){
            LogUtils.i("startDownload--------");
        }

        public int getProgress(){
            LogUtils.i("getProgress---------");
            return 0;
        }
    }

}
