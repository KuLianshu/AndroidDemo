package com.wly.mvvm.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<M : BaseModel<*>> : ViewModel(){

    protected var model : M?
    init {
        model = createModel()
    }

    protected abstract fun createModel(): M

    override fun onCleared() {
        super.onCleared()
        model?.destroy()
        model = null
    }


}