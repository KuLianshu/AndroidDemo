package com.example.coroutinescopeexceptiondemo

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {
    override val key =  CoroutineExceptionHandler
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.d("WLY","Unhandled Coroutine Exception: $exception")
    }
}