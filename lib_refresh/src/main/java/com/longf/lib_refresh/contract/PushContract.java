package com.longf.lib_refresh.contract;

public interface PushContract {
    /**
     * 手指上滑下滑的回调
     * @param enable
     */
    void onPushEnable(boolean enable);

    /**
     * 手指松开的回调
     */
    void onLoadMore();
}
