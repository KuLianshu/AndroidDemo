package com.example.aidldemo;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidldemo.domain.BinderPool;
import com.example.aidldemo.domain.ComputeImpl;
import com.example.aidldemo.domain.SecurityCenterImpl;

public class BinderPoolTestActivity extends AppCompatActivity {

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter =  SecurityCenterImpl.asInterface(securityBinder);
        Log.i("WLY","visit ISecurityCenter");
        String msg = "hello world!";
        Log.i("WLY",msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            Log.i("WLY","encrypt:"+password);
            Log.i("WLY","decrypt:"+mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Log.i("WLY","visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            Log.i("WLY","3+5="+mCompute.add(3,5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
