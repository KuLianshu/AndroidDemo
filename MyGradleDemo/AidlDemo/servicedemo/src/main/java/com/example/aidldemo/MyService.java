package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;


import com.example.mylibrary.Book;
import com.example.mylibrary.IMyAidlInterface;
import com.example.mylibrary.ISearchBooksCallback;
import com.example.mylibrary.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class MyService extends Service {
    public static final String TAG = "MyService";
    private List<Book> books;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        return binder;
    }

    private final IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub() {
        @Override
        public int borrowBook(String bookName) throws RemoteException {
            //实现一系列 借书 的逻辑后，返回结果码，比如:0为失败，1为成功
            if (books == null) {
                books = new ArrayList<>();
            }

            if (books.size() > 0) {
                //图书馆有图书，减掉1
                books.remove(0);
                return 1;
            } else {
                //图书馆没有图书，借书失败
                return 0;
            }
        }

        @Override
        public int returnBook(String bookName) throws RemoteException {
            //实现一系列 还书 的逻辑后，返回结果码，比如:0为失败，1为成功
            if (books == null) {
                books = new ArrayList<>();
            }
            books.add(new Book(bookName, 1));
            return 1;
        }

        @Override
        public int borrowOtherBook(Book book) throws RemoteException {
            LogUtil.d(TAG, "接收到的book：" + book.getName());
            //给book重新赋值
            book.setName("百年孤独");
            book.setNum(1);
            return 1;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            if (books == null) {
                books = new ArrayList<>();
            }
            return books;
        }

        @Override
        public void getBooks(ISearchBooksCallback callback) throws RemoteException {
            try {
                //模拟耗时
                sleep(4000);

                if (books == null) {
                    books = new ArrayList<>();
                }
                RemoteCallbackList<ISearchBooksCallback> remoteCallbackList = new RemoteCallbackList<>();
                remoteCallbackList.register(callback);
                final int len = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < len; i++) {
                    LogUtil.d(TAG, "准备回调了--数量：" + books.size());
                    remoteCallbackList.getBroadcastItem(i).onSuccess(books);
                    LogUtil.d(TAG, "回调了--数量" + books.size());
                }
                LogUtil.d(TAG, "准备结束广播-数量-" + books.size());
                remoteCallbackList.finishBroadcast();
                LogUtil.d(TAG, "结束广播-数量-" + books.size());
            } catch (InterruptedException | RemoteException e) {
                e.printStackTrace();
            }
        }
    };
}