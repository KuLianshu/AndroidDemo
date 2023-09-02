package com.example.databinding.model

import android.util.Log
import androidx.databinding.ObservableField
import com.example.databinding.entity.User

class MyUserViewModel {
    private val userObservableField: ObservableField<User>

    init {
        val user = User("Jack")
        userObservableField = ObservableField()
        userObservableField.set(user)
    }

    var userName:String?
    get() = userObservableField.get()!!.userName
    set(userName) {
        Log.i("WLY","set username : $userName")
        userObservableField.get()!!.userName = userName!!
    }



}


