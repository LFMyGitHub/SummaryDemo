package com.longf.lib_common.mvp.view;

import com.longf.lib_common.mvp.contract.BaseRefreshContract;

import java.util.List;

public interface BaseRefreshView<T> extends BaseRefreshContract.View {
    //刷新数据
    void refreshData(List<T> data);
    //加载更多
    void loadMoreData(List<T> data);
}
