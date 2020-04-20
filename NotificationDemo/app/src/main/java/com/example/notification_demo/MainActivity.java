package com.example.notification_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.notification_demo.utils.AppConstant;

import static com.example.notification_demo.utils.AppConstant.ChannelId_Chat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NOTIFICATION_TAG="WLY";
    public static final int DEFAULT_NOTIFICATION_ID=1;

    private NotificationManager mNotificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initEvent();


    }

    private void initEvent() {
        mNotificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void initView() {

        findViewById(R.id.btn_remove_all_notification_activity_main).setOnClickListener(this);
        findViewById(R.id.btn_send_notification_activity_main).setOnClickListener(this);
        findViewById(R.id.btn_remove_notification_activity_main).setOnClickListener(this);
        findViewById(R.id.btn_send_notification_with_tag_activity_main).setOnClickListener(this);
        findViewById(R.id.btn_remove_notification_with_tag_activity_main).setOnClickListener(this);
        findViewById(R.id.btn_send_ten_notification_activity_main).setOnClickListener(this);
        findViewById(R.id.btn_send_flag_no_clear_notification_activity_main).setOnClickListener(this);
        findViewById(R.id.btn_send_flag_ongoing_event_notification_activity_main).setOnClickListener(this);
        findViewById(R.id.btn_send_flag_auto_cancel_notification_activity_main).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (mNotificationManager!=null){
            switch (v.getId()){
                case R.id.btn_remove_all_notification_activity_main:
                    //移除当前 Context 下所有 Notification,包括 FLAG_NO_CLEAR 和 FLAG_ONGOING_EVENT
                    mNotificationManager.cancelAll();
                    break;
                case R.id.btn_send_notification_activity_main:
                    //发送一个 Notification,此处 ID = 1
                    sendNotification();
                    break;
                case R.id.btn_remove_notification_activity_main:
                    //移除 ID = 1 的 Notification,注意:该方法只针对当前 Context。
                    mNotificationManager.cancel(DEFAULT_NOTIFICATION_ID);
                    break;
                case R.id.btn_send_notification_with_tag_activity_main:
                    //发送一个 ID = 1 并且 TAG = littlejie 的 Notification
                    //注意:此处发送的通知与 sendNotification() 发送的通知并不冲突
                    //因为此处的 Notification 带有 TAG
                    sendNotificationWithTag();
                    break;
                case R.id.btn_remove_notification_with_tag_activity_main:
                    //移除一个 ID = 1 并且 TAG = WLY 的 Notification
                    //注意:此处移除的通知与 NotificationManager.cancel(int id) 移除通知并不冲突
                    //因为此处的 Notification 带有 TAG
                    mNotificationManager.cancel(NOTIFICATION_TAG, DEFAULT_NOTIFICATION_ID);
                    break;
                case R.id.btn_send_ten_notification_activity_main:
                    //连续发十条 Notification
                    sendTenNotifications();
                    break;
                case R.id.btn_send_flag_no_clear_notification_activity_main:
                    //发送 ID = 1, flag = FLAG_NO_CLEAR 的 Notification
                    //下面两个 Notification 的 ID 都为 1,会发现 ID 相等的 Notification 会被最新的替换掉
                    sendFlagNoClearNotification();
                    break;
                case R.id.btn_send_flag_auto_cancel_notification_activity_main:
                    sendFlagOngoingEventNotification();
                    break;
                case R.id.btn_send_flag_ongoing_event_notification_activity_main:
                    sendFlagAutoCancelNotification();
                    break;
            }
        }

    }

    private void sendFlagOngoingEventNotification() {

    }

    /**
     * 设置FLAG_AUTO_CANCEL
     * 该flag表示用户点击后自动消失
     */
    private void sendFlagAutoCancelNotification() {


    }



    /**
     * 设置FLAG_NO_CLEAR
     * 该flag表示该通知不能被状态栏的清除键给清除掉，也不能被手动
     * 清除，但通过cancel()方法清除。Notification.flags属性可以、
     * 通过|=运算叠加效果
     *
     */
    private void sendFlagNoClearNotification() {
        Notification notification=createNotification();
        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
        //flags 可以通过 |= 运算叠加效果
        notification.flags|=Notification.FLAG_NO_CLEAR;
        mNotificationManager.notify(DEFAULT_NOTIFICATION_ID,notification);
    }

    /**
     * 循环发送十个通知
     */
    private void sendTenNotifications() {
        for (int i=0;i<10;i++){
            Notification notification=createNotification();
            mNotificationManager.notify(i,notification);
        }
    }

    /**
     * 使用notify(String tag, int id, Notification notification)方法发送通知是使用notify()
     *  移除对应通知需使用 cancel(String tag, int id)
     */
    private void sendNotificationWithTag() {
        Notification notification=createNotification();
        mNotificationManager.notify(NOTIFICATION_TAG,DEFAULT_NOTIFICATION_ID,notification);
    }

    /**
     * 发送最简单的通知，该通知的ID=1
     */
    private void sendNotification() {
        Notification notification=createNotification();
        mNotificationManager.notify(DEFAULT_NOTIFICATION_ID,notification);

    }

    private Notification createNotification(){
        Notification notification;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //在Android 8中，Notification.Builder(this)已经被废弃
            notification=new Notification.Builder(this, ChannelId_Chat)
                    .setContentTitle("午饭")
                    .setContentText("中午吃啥？？")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    //必须加上这行，setAutoCancel才起作用
//                    .setContentIntent(PendingIntent.getActivity(this,0,new Intent(),0))
//                    .setAutoCancel(true)
                    .build();
        }else {
            notification=new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("晚饭")
                    .setContentText("晚上吃啥？？")
//                    .setContentIntent(PendingIntent.getActivity(this,0,new Intent(),0))
//                    .setAutoCancel(true)
                    .build();

        }
        return notification;
    }
}
