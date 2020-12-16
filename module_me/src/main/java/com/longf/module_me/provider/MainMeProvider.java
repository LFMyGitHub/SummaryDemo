package com.longf.module_me.provider;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.longf.lib_common.provider.IMeProvider;
import com.longf.module_me.fragment.MainMeFragment;

@Route(path = "/me/main",name = "我的")
public class MainMeProvider implements IMeProvider {
    @Override
    public Fragment getMainMeFragment() {
        return new MainMeFragment();
    }

    @Override
    public void init(Context context) {

    }
}
