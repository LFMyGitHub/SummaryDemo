package com.longf.module_page3.provider;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.longf.lib_common.provider.IPage3Provider;
import com.longf.module_page3.fragment.MainPage3Fragment;

@Route(path = "/page3/main",name = "消息")
public class MainPage3Provider implements IPage3Provider {

    @Override
    public void init(Context context) {

    }

    @Override
    public Fragment getMainPage3Fragment() {
        return new MainPage3Fragment();
    }
}
