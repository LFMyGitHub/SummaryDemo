package com.longf.lib_common.mvp.view;

import androidx.annotation.DrawableRes;

public interface INoDataView {
    //显示无数据View
    void showNoDataView();
    //隐藏无数据View
    void hideNoDataView();

    //显示指定资源的无数据View
    void showNoDataView(@DrawableRes int resid);
}
