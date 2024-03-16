package com.example.okhttpdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.okhttpdemo.entity.BrandBean
import com.example.okhttpdemo.utils.XMLUtils
import com.thoughtworks.xstream.XStream


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val okHttpUtils = OkHttpUtils()
//        okHttpUtils.get()
//        okHttpUtils.post()

        XMLUtils().XmlToBean1(this);
//        XMLUtils().BeanToXml();

    }




}