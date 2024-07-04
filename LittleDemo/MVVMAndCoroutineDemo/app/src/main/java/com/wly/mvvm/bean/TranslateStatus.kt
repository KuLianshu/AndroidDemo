package com.wly.mvvm.bean

sealed class TranslateStatus{

    data class Progress(val progress: Int): TranslateStatus()
    data class Success(val data: String): TranslateStatus()
    data class Failure(val message: String): TranslateStatus()

}
