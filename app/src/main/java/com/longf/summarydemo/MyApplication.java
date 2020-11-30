package com.longf.summarydemo;

import com.longf.lib_api.RetrofitManager;
import com.longf.lib_common.BaseApplication;

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.init(this);
    }
}
