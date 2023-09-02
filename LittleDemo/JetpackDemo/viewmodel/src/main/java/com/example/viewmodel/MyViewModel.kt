package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MyViewModel(private val application: Application) : AndroidViewModel(application) {
    var number = 0
}

