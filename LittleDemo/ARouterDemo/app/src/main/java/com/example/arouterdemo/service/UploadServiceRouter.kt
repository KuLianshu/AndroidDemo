package com.example.arouterdemo.service

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider


interface IRouteService: IProvider{
    fun startUploadService()
}
@Route(path = "/service/upload")
class UploadServiceRouter : IRouteService{
    lateinit var context: Context

    override fun startUploadService() {
        val intent = Intent(context, UploadService::class.java)
        context.startService(intent)
    }

    override fun init(context: Context) {
        this.context = context
        startUploadService()
    }
}