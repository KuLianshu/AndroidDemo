package com.example.chapter5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_send_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
    }

    private void sendNotification() {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            String channelId="ChannelId";//通知渠道
            Notification notification=new Notification.Builder(this,"ChannelId")
                    .setChannelId(channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("标题")
                    .setContentText("通知内容")
                    .build();
            NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel=new NotificationChannel(
                    channelId,
                    "Notification Name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
                notificationManager.notify(1,notification);
            }
        }else {
            Intent intent = new Intent(this,DemoActivity_1.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"test")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setAutoCancel(true)
                    .setContentText("标题")
                    .setContentText("内容")
                    .setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1,builder.build());
        }



    }
}