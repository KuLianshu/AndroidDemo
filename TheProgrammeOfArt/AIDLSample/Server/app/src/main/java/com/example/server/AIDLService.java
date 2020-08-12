package com.example.server;import android.app.Service;import android.content.Intent;import android.os.IBinder;import android.os.RemoteCallbackList;import android.os.RemoteException;import android.util.Log;import androidx.annotation.Nullable;import com.example.server.utils.LogUtils;import java.util.ArrayList;import java.util.List;import java.util.concurrent.CopyOnWriteArrayList;import java.util.concurrent.atomic.AtomicBoolean;public class AIDLService extends Service {    private List<Book> bookList;    public AIDLService(){}    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();    private RemoteCallbackList<IOnNewBookArrivedListener> mListener = new RemoteCallbackList<>();    @Override    public void onCreate() {        super.onCreate();        bookList = new ArrayList<>();        initData();    }    private void initData() {        Book book1 = new Book("活着");        Book book2 = new Book("或者");        Book book3 = new Book("叶应是叶");//        Book book4 = new Book("https://github.com/leavesC");//        Book book5 = new Book("http://www.jianshu.com/u/9df45b87cfdf");//        Book book6 = new Book("http://blog.csdn.net/new_one_object");        bookList.add(book1);        bookList.add(book2);        bookList.add(book3);//        bookList.add(book4);//        bookList.add(book5);//        bookList.add(book6);       new Thread(new ServiceWorker()).start();    }    private void onNewBookArrived(Book book) throws RemoteException{        mBookList.add(book);        final int N = mListener.beginBroadcast();        for (int i =0;i<N;i++){            IOnNewBookArrivedListener l = mListener.getBroadcastItem(i);            if (l!=null){                l.onNewBookArrived(book);            }        }        mListener.finishBroadcast();    }    private class ServiceWorker implements Runnable{        @Override        public void run() {            while (!mIsServiceDestroyed.get()){                try {                    //每隔5秒就向书库中新增一本书                    Thread.sleep(5000);                } catch (InterruptedException e) {                    e.printStackTrace();                }                int bookId = mBookList.size()+1;                Book newBook = new Book("new book#"+bookId);                try {                    //通知感兴趣的用户                    onNewBookArrived(newBook);                } catch (RemoteException e) {                    e.printStackTrace();                }            }        }    }    private final BookController.Stub stub = new BookController.Stub() {        @Override        public List<Book> getBookList() throws RemoteException {            return bookList;        }        @Override        public void addBookInOut(Book book) throws RemoteException {            if (book!=null){//                book.setName("服务器改了新的书名 InOut");                bookList.add(book);                LogUtils.i("服务器新增一本书："+book.getName());            }else {                LogUtils.i("接收到了一个空对象 InOut");            }        }        @Override        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {            mListener.register(listener);            LogUtils.i("registerListener, size: "+mListener.getRegisteredCallbackCount());        }        @Override        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {            mListener.unregister(listener);            LogUtils.i("unregisterListener, current size: "+mListener.getRegisteredCallbackCount());        }    };    @Nullable    @Override    public IBinder onBind(Intent intent) {        return stub;    }}