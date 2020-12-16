package com.longf.lib_common.mvp.view;

import android.content.Context;

public interface BaseView extends ILoadView,INoDataView,ITransView,INetErrView{
    void initView();
    void initListener();
    void initData();
    void finishActivity();
    Context getContext();
}
