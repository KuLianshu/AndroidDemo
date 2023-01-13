package com.example.clientdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.mylibrary.Book;
import com.example.mylibrary.IMyAidlInterface;
import com.example.mylibrary.ISearchBooksCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity>>";
    private IMyAidlInterface iMyAidlInterface;
    private boolean isConnected = false;//是否连接服务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isConnected) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isConnected) {
            unbindService(mServiceConnection);
            isConnected = false;
        }
    }

    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.example.aidldemo.MyService");
        intent.setPackage("com.example.aidldemo");

        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected");
            //获得 aidl定义的接口持有类
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
            isConnected = false;
        }
    };

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void checkConnected() {
        if (!isConnected) {
            showToast("未连接服务");
            return;
        }
    }

    public void borrowBook(View view) {
        checkConnected();
        try {
            int resultCode = iMyAidlInterface.borrowBook("流浪地球");
            showToast(resultCode == 1 ? "借书成功，图书-1" : "没有图书了，借书失败");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(View view) {
        checkConnected();
        try {
            int resultCode = iMyAidlInterface.returnBook("流浪地球");
            showToast(resultCode == 1 ? "还书成功，图书+1" : "还书失败");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getBooksNum(View view) {
        checkConnected();
        try {
            List<Book> books = iMyAidlInterface.getBookList();
            int booksNum = books.size();
            showToast("剩余图书数量：" + booksNum);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getBooksNumDelay(View view) {
        checkConnected();
        try {
            iMyAidlInterface.getBooks(new ISearchBooksCallback.Stub() {
                @Override
                public void onSuccess(final List<Book> books) throws RemoteException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "客户端收到回调了 onSuccess--数量：" + books.size());
                            showToast("剩余图书数量(延时回调)：" + books.size());
                        }
                    });
                }

                @Override
                public void onError(String msg) throws RemoteException {

                }
            });

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 借到一本被服务器改变了的书
     * @param view
     */
    public void borrowOtherBook(View view) {
        checkConnected();
        try {
            Book book = new Book("流浪地球", 1);
            int resultCode = iMyAidlInterface.borrowOtherBook(book);
            //打印被服务器改变的值
            Log.d(TAG,"借到的图书为："+book.getName());
            showToast(resultCode == 1 ? "成功借到图书:" + book.getName() : "借书失败");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
