package com.longf.lib_common.provider;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IMainProvider extends IProvider {
    Fragment getMainFragment();
}
