package com.hero.mvvmdemo.mvvm.base;

import androidx.lifecycle.ViewModel;

/**
 * <pre>
 * ViewModel的基类
 * </pre>
 */
public abstract class BaseViewModel<M extends BaseModel> extends ViewModel {

    protected M model;

    public BaseViewModel() {
        model = createModel();
    }

    protected abstract M createModel();

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.destory();
        }
        model= null;
    }
}