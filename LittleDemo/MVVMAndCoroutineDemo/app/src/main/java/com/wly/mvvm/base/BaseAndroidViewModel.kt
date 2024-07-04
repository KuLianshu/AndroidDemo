package com.wly.mvvm.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseAndroidViewModel<M : BaseModel<*>>(application: Application)
    : AndroidViewModel(application){

    protected var model: M?
    init {
        model = createModel()
    }

    abstract fun createModel(): M

    override fun onCleared() {
        super.onCleared()
        model?.destroy()
        model = null
    }


}