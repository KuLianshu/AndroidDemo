package com.example.lifecycle.observer

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class ApplicationObserver: LifecycleEventObserver {
    private val TAG = "WLY"

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.i(TAG,"----------  ON_CREATE  ---------")
            }
            Lifecycle.Event.ON_START -> {
                Log.i(TAG,"----------  ON_START  ---------")

            }
            Lifecycle.Event.ON_RESUME -> {
                Log.i(TAG,"----------  ON_RESUME  ---------")

            }
            Lifecycle.Event.ON_PAUSE ->{
                Log.i(TAG,"----------  ON_PAUSE  ---------")

            }
            Lifecycle.Event.ON_STOP ->{
                Log.i(TAG,"----------  ON_STOP  ---------")

            }
            Lifecycle.Event.ON_DESTROY ->{
                Log.i(TAG,"----------  ON_DESTROY  ---------")

            }
            else -> {}
        }
    }
}