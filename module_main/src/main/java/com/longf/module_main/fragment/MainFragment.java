package com.longf.module_main.fragment;

import android.view.View;

import com.longf.lib_common.mvp.BaseMvpFragment;
import com.longf.module_main.R;

public class MainFragment extends BaseMvpFragment {

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
        return null;
    }
}
