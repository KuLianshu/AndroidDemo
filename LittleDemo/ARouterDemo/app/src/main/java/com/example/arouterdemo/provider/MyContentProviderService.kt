package com.example.arouterdemo.provider

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider

interface ContentProviderService : IProvider {
    fun getContentUri(): Uri
    fun queryData(): Cursor?
}

@Route(path = "/service/content")
class MyContentProviderService : ContentProviderService {
    lateinit var context: Context

    override fun getContentUri(): Uri {
        return Uri.parse("content://com.example.provider/data")
    }

    override fun queryData(): Cursor? {
        return context.contentResolver?.query(
            getContentUri(),
            null, null, null, null
        )
    }

    override fun init(context: Context) {
        // 初始化
        this.context = context
    }
}