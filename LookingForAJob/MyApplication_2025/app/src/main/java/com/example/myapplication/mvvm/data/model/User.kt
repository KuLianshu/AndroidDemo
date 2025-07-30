package com.example.myapplication.mvvm.data.model

data class User(
    val login: String,
    val id: Long,
    val avatar_url: String,
    val url: String,
    val name: String?,
    val bio: String?
)    