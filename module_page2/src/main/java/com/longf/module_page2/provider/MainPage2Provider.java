package com.longf.module_page2.provider;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.longf.lib_common.provider.IPage2Provider;
import com.longf.module_page2.fragment.MainPage2Fragment;

@Route(path = "/page2/main",name = "发现")
public class MainPage2Provider implements IPage2Provider {

    @Override
    public void init(Context context) {

    }

    @Override
    public Fragment getMainPage2Fragment() {
        return new MainPage2Fragment();
    }
}
