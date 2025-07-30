package com.example.arouterdemo.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider
import com.example.common.utils.Constants

interface BroadcastService : IProvider {
    fun registerReceiver(context: Context)
    fun unregisterReceiver(context: Context)
}

@Route(path = Constants.PATH_APP_BROADCAST)
class MyBroadcastService : BroadcastService, BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // 处理广播
        Log.i("WLY","RECEIVE BROADCAST ===")
    }

    override fun registerReceiver(context: Context) {
        val filter = IntentFilter().apply {
            addAction(Constants.PATH_APP_BROADCAST_ACTION)
        }
        context.registerReceiver(this, filter)
    }

    override fun unregisterReceiver(context: Context) {
        context.unregisterReceiver(this)
    }

    override fun init(context: Context) {
        registerReceiver(context)
    }
}