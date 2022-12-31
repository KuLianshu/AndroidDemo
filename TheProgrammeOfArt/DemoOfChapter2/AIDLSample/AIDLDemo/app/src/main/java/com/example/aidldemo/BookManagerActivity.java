package com.example.aidldemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookManagerActivity extends Activity {

    private static final String TAG = "WLY";

    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mRemoteBookManager;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG,"receive new book : "+msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                mRemoteBookManager = bookManager;
                        List<Book> list = bookManager.getBookList();
                Log.i(TAG,"query book list, list type: "+list.getClass().getCanonicalName());
                Log.i(TAG,"1，query book list: "+list.toString());

                Book newBook = new Book(3,"Android开发，从入门到放弃");
                bookManager.addBook(newBook);
                Log.i(TAG,"add book: "+newBook.toString());
                List<Book> newList = bookManager.getBookList();
                Log.i(TAG,"2，query book list: "+newList.toString());
                bookManager.registerListener(mOnNewBookArrivedListener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteBookManager = null;
            Log.d(TAG,"binder died.");
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,newBook).sendToTarget();
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager!=null&&mRemoteBookManager.asBinder().isBinderAlive()){
            try {
                Log.i(TAG,"unregister listener:"+mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
