package com.example.ipcdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ipcdemo.utils.LogUtils;
import com.example.ipcdemo.utils.MyConstants;

/**
 * 服务端代码
 */
public class MessengerService extends Service {

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MyConstants.MSG_FROM_CLIENT) {
                LogUtils.i("receive msg from client: " + msg.getData().getString(MyConstants.MSG_KEY));
                //回复消息给客户端
                Messenger client = msg.replyTo;
                Message replyMessage = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                Bundle bundle = new Bundle();
                bundle.putString(MyConstants.MSG_KEY1,"哥们，你的消息已收到。");
                replyMessage.setData(bundle);
                try {
                    client.send(replyMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }

    private final Messenger messenger = new Messenger(new MessengerHandler());


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
