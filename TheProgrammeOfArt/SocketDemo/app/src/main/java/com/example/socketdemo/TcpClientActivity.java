package com.example.socketdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socketdemo.service.TCPServerService;
import com.example.socketdemo.utils.LogUtils;
import com.example.socketdemo.utils.MyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TcpClientActivity extends Activity implements View.OnClickListener {

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Button mSendButton;
    private TextView mMessageTextView;
    private EditText mMessageEditText;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_RECEIVE_NEW_MSG:
                    mMessageTextView.setText(mMessageTextView.getText()+(String)msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    mSendButton.setEnabled(true);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);
        mSendButton = findViewById(R.id.btn_send_message);
        mMessageTextView = findViewById(R.id.tv_message);
        mMessageEditText = findViewById(R.id.et_message);

        mSendButton.setOnClickListener(this);

        Intent service = new Intent(this, TCPServerService.class);
        startService(service);
        new Thread(){
            @Override
            public void run() {
                super.run();
                connectTCPServer();
            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClientSocket!=null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_send_message){
            final String msg = mMessageEditText .getText().toString();
            if (!TextUtils.isEmpty(msg)&&mPrintWriter!=null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPrintWriter.println(msg);
                    }
                }).start();
                mMessageEditText.setText("");
                String time = formatDateTime(System.currentTimeMillis());
                final String showMsg = "self "+ time+":"+msg+"\n";
                String content = mMessageTextView.getText()+showMsg;
                mMessageTextView.setText(content);
            }
        }
    }

    private String formatDateTime(long time){
        return new SimpleDateFormat("(HH:mm:ss", Locale.CHINA).format(new Date(time));
    }

    private void connectTCPServer(){
        Socket socket = null;
        while (socket == null){
            try {
                socket = new Socket("localhost",8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                LogUtils.i("connect server success");

            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
                LogUtils.i("connect typ server failed, retry...");
            }
        }

        try {
            //接收服务器的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TcpClientActivity.this.isFinishing()){
                String msg = br.readLine();
                LogUtils.i("receive : "+msg);
                if (msg!=null){
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showedMsg= "server"+time+":"+msg+"\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showedMsg).sendToTarget();
                }
            }
            LogUtils.i("quit...");
            MyUtils.close(mPrintWriter);
            MyUtils.close(br);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
