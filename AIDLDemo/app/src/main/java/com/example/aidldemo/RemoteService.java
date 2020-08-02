package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RemoteService extends Service {

    private final String TAG = "SERVER";

    private List<Book> bookList;



    @Override
    public void onCreate() {
        super.onCreate();
        bookList = new ArrayList<>();
        initData();

    }


    private int index;
    private void initData() {
        index = bookList.size()-1;
        Book book1 = new Book(index++,"活着");
        Book book2 = new Book(index++,"或者");
        Book book3 = new Book(index++,"叶应是叶");
        Book book4 = new Book(index++,"https://github.com/leavesC");
        Book book5 = new Book(index++,"http://www.jianshu.com/u/9df45b87cfdf");
        Book book6 = new Book(index++,"http://blog.csdn.net/new_one_object");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);
        bookList.add(book6);
    }



    IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;

        };

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book!=null){
//                book.setName("服务器改了新的书名 InOut");
                bookList.add(book);
                Log.i(TAG,"服务器新增一本书："+book.getBookName());

            }else {
                Log.i(TAG,"接收到了一个空对象 InOut");
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
