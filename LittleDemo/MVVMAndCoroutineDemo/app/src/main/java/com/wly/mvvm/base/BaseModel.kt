package com.wly.mvvm.base

import java.lang.ref.WeakReference

abstract class BaseModel<I>(icCallBack: I)  {

    protected var  iCallBackWeakRef : WeakReference<I> ? = WeakReference<I>(icCallBack)

    fun getCallBack(): I?{
        return iCallBackWeakRef?.get()
    }

    fun isCallBackDestroy(): Boolean{
        if (iCallBackWeakRef == null){
            return true
        }
        iCallBackWeakRef!!.get() ?: return true
        return false
    }


    fun destroy(){
        val icCallBack: I? = iCallBackWeakRef?.get()
        if (icCallBack!=null){
            iCallBackWeakRef?.clear()
        }
        iCallBackWeakRef = null
    }




}