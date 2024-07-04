package com.wly.mvvm.interfaces

import android.view.View

interface OnClickPresenter : View.OnClickListener{
    override fun onClick(v: View)
}