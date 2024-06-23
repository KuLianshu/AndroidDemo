package com.hero.mvvmdemo.mvvm.interfaces;

import android.view.View;

/**
 * <pre>
 * 点击事件，使用自定义接口，在activity中统一调用
 * </pre>
 */
public interface OnClickPresenter extends View.OnClickListener{
    @Override
    void onClick(View v);
}
