package com.example.databinding.listener

import android.content.Context
import android.view.View
import android.widget.Toast

class EventHandlerListener(private val context: Context) {

    fun buttonOnClick(view: View){
        Toast.makeText(context,"票数加一",Toast.LENGTH_SHORT).show()
    }


}