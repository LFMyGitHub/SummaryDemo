package com.longf.lib_refresh.contract;

public interface PullContract {
    /**
     * 手指上滑下滑的回调
     * @param enable
     */
    void onPullEnable(boolean enable);

    /**
     * 手指松开的回调
     */
    void onRefresh();
}
