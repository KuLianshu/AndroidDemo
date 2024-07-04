package com.wly.mvvm.interfaces

interface OnTranslateCallBack {

    fun onResponse(response: String)
    fun onFailure(t: Throwable)

}