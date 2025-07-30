package com.example.arouterdemo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class UploadService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("WLY","--------- onCreate -------")
    }

}