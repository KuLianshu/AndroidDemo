package com.example.lifecycle.widget


import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.Chronometer
import androidx.lifecycle.*

class MyChronometer  constructor(context: Context?, attrs: AttributeSet?) : Chronometer(context, attrs), LifecycleEventObserver {

    private var elapsedTime: Long = 0L

    constructor(context: Context?): this(context,null)

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_CREATE -> {}
            Lifecycle.Event.ON_RESUME -> {
                base = SystemClock.elapsedRealtime() - elapsedTime
                start()
            }
            Lifecycle.Event.ON_PAUSE ->{
                elapsedTime = SystemClock.elapsedRealtime() - base
                stop()
            }
            Lifecycle.Event.ON_START -> {}
            else -> {}
        }
    }

    //实现LifecycleObserver接口的写法已过时
//    val myEventObserver = object : LifecycleObserver {
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//        fun onStart() {
//
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//        fun onCreat() {
//
//        }
//    }
}