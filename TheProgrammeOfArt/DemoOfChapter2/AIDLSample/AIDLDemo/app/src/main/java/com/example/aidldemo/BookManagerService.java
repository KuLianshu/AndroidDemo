package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private static final String TAG = "BMS";

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    //CopyOnWriteArrayList支持并发读写
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(com.example.aidldemo.IOnNewBookArrivedListener listener) throws RemoteException {
            if (!mListenerList.contains(listener)){
                mListenerList.add(listener);
            }else {
                Log.d(TAG,"already exists.");
            }
            Log.d(TAG,"registerListener, size: "+mListenerList.size());
        }

        @Override
        public void unregisterListener(com.example.aidldemo.IOnNewBookArrivedListener listener) throws RemoteException {
                if (mListenerList.contains(listener)){
                    mListenerList.remove(listener);
                }else {
                    Log.d(TAG,"not found, can not unregister.");
                }
                Log.d(TAG,"unregisterListener, current size:"+mListenerList.size());
        }


    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"IOS"));

        new Thread(new ServiceWorker()).start();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(false);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        Log.d(TAG,"onNewBookArrived, notify listeners:"+mListenerList.size());
        for (int i=0; i<mListenerList.size(); i++){
            IOnNewBookArrivedListener listener = mListenerList.get(i);
            Log.d(TAG, "onNewBookArrived, notify listener:"+listener);
            Log.d(TAG,"size:"+mListenerList.size()+", i:"+i);
            listener.onNewBookArrived(book);
        }
    }


    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size()+1;
                Book newBook = new Book(bookId,"new book#"+bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
