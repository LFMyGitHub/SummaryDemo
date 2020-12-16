package com.longf.lib_common.mvp.presenter;

import android.content.Context;

import com.longf.lib_common.mvp.contract.BaseRefreshContract;
import com.longf.lib_common.mvp.model.BaseModel;
import com.longf.lib_common.mvp.view.BaseRefreshView;

public abstract class BaseRefreshPresenter<M extends BaseModel,V extends BaseRefreshView<T>,T> extends BasePresenter<M,V> implements BaseRefreshContract.Presenter {

    public BaseRefreshPresenter(Context context, V view, M model) {
        super(context, view, model);
    }
}
