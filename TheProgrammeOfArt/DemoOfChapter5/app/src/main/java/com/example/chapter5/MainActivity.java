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
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_send_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendNotification();
                sendMyNotification();
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

    public void sendMyNotification(){
        Intent intent = new Intent(this,DemoActivity_1.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.msg,"chapter_5");
        remoteViews.setImageViewResource(R.id.icon,R.mipmap.ic_launcher_round);
        PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(this,0,
                new Intent(this,DemoActivity_2.class),PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2,openActivity2PendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"test")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentText("标题")
                .setContentText("内容")
                .setCustomContentView(remoteViews)
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification);
    }


}