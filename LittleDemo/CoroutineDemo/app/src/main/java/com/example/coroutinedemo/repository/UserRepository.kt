package com.example.coroutinedemo.repository

import kotlinx.coroutines.delay

class UserRepository {

    //一般Repository中写网络请求
    suspend fun getUser(): String{
        delay(1500)
        val r = (10..50)
        return "${r.random()}"
    }

}