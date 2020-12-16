package com.longf.module_main.provider;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.longf.lib_common.provider.IMainProvider;
import com.longf.module_main.fragment.MainFragment;

@Route(path = "/main/main",name = "首页")
public class MainProvider implements IMainProvider {
    @Override
    public Fragment getMainFragment() {
        return new MainFragment();
    }

    @Override
    public void init(Context context) {

    }
}
