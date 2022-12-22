package com.example.aidldemo.domain;

import android.os.RemoteException;

import com.example.aidldemo.ICompute;

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
