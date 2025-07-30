package com.example.myapplication.mvvm.data.repository


import com.example.myapplication.retrofit.ApiService

class UserRepository(private val apiService: ApiService) {
    suspend fun fetchUser(username: String) = apiService.getUser(username)
}    