package com.example.databinding.model

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.databinding.BR
import com.example.databinding.entity.User


class UserViewModel : BaseObservable(){


    var user: User = User("Jack")

    @Bindable
    fun getUserName(): String = user.userName

    fun setUserName(userName: String?){
        userName?.let {
            if (it != user.userName){
                user.userName = it
                Log.i("WLY","set username : $userName")
                notifyPropertyChanged(BR.userName)
            }

        }

    }
}

