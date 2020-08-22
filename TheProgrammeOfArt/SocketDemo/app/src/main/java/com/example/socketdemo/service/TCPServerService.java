package com.example.socketdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.socketdemo.utils.LogUtils;
import com.example.socketdemo.utils.MyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {

    private boolean mIsServiceDestroyed = false;

    private String [] mDefinedMessages = new String[]{
      "你好呀",
      "请告诉我你的名字",
      "你大爷",
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TcpServer()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroyed = true;
    }

    private class TcpServer implements  Runnable{

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                //监听本地8688端口
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestroyed){
                Socket client =null;
                try {
                    client = serverSocket.accept();
                    LogUtils.i("accept");
                    final Socket finalClient = client;
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                responseClient(finalClient);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static int index = 0;
    private void responseClient(Socket client) throws IOException{
        //用于接收客户端信息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);

        out.println("欢迎来到聊天室!");
        while (!mIsServiceDestroyed){
            String str = in.readLine();
            LogUtils.i("msg form client: "+ str);
            if (str==null){
                //客户端断开连接
                break;
            }

            int i = index%3;
            index++;

            String msg = mDefinedMessages[i];
            out.println("send : "+msg);
        }
        LogUtils.i("client quit.");
        //关闭流
        MyUtils.close(in);
        MyUtils.close(out);
        client.close();

    }


}
