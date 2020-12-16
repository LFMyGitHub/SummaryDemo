package com.longf.module_main.fragment;

import android.view.View;

import com.longf.lib_common.mvp.BaseRefreshFragment;
import com.longf.module_main.R;

import java.util.List;

public class MainFragment extends BaseRefreshFragment {

    public MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    protected int onBindRreshLayout() {
        return R.id.refview_main_list;
    }

    @Override
    public void injectPresenter() {

    }

    @Override
    public int onBindLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public String getToolbarTitle() {
        return "首页";
    }

    @Override
    public void refreshData(List data) {

    }

    @Override
    public void loadMoreData(List data) {

    }

    @Override
    public void onRefreshEvent() {

    }

    @Override
    public void onLoadMoreEvent() {

    }

    @Override
    public void onAutoLoadEvent() {

    }
}
