package com.example.arouterdemo.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.example.arouterdemo.R
import com.example.common.utils.Constants

class FragmentActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val fragment = ARouter.getInstance()
            .build(Constants.PATH_APP_FRAGMENT)
            .navigation() as Fragment

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()

    }

}