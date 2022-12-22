package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import androidx.annotation.Nullable;

/**
 * 服务端
 */
public class RemoteService extends Service {

    IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public HelloMsg sayHello() throws RemoteException {
            return new HelloMsg("msg from service at Thread "
                    + Thread.currentThread().getName()
                    +",\n tid is "+ Thread.currentThread().getId()
                    +",\n main thread id is"
                    +getMainLooper().getThread().getId(),
                    Process.myPid());

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
