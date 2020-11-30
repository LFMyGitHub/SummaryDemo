package com.longf.module_main;

import com.longf.lib_common.mvvm.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public int onBindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean enableToolbar() {
        return false;
    }
}
