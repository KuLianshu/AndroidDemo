package com.example.databinding.utils

fun getStart(start: Int): String = when(start){
    1 ->"一星"
    2 ->"二星"
    3 ->"三星"
    4 ->"四星"
    5 ->"五星"
    else -> "无星"
}