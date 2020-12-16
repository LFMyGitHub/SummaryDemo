package com.longf.module_main;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.longf.lib_common.mvp.BaseActivity;
import com.longf.lib_common.provider.IMainProvider;
import com.longf.lib_common.provider.IMeProvider;
import com.longf.module_main.entity.MainChannel;

public class MainActivity extends BaseActivity {
    @Autowired(name = "/main/main")
    IMainProvider mIMainProvider;

    @Autowired(name = "/me/main")
    IMeProvider mIMeProvider;

    private Fragment mMainFragment;
    private Fragment mMeFragment;
    private Fragment mCurrFragment;

    @Override
    public int onBindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int i = menuItem.getItemId();
                if (i == R.id.navigation_trip) {
                    switchContent(mCurrFragment, mMainFragment, MainChannel.MAIN.name);
                    mCurrFragment = mMainFragment;

                    return true;
                } else if (i == R.id.navigation_me) {
                    switchContent(mCurrFragment, mMeFragment, MainChannel.ME.name);
                    mCurrFragment = mMeFragment;
                    return true;
                }
                return false;
            }
        });
        if(mIMainProvider != null){
            mMainFragment = mIMainProvider.getMainFragment();
        }
        if(mIMeProvider != null){
            mMeFragment = mIMeProvider.getMainMeFragment();
        }
        mCurrFragment = mMainFragment;
        if(mMainFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mMainFragment, MainChannel.MAIN.name).commit();
        }
    }

    @Override
    public void initData() {

    }

    /**
     * 首页Fragment切换
     *
     * @param from
     * @param to
     * @param tag
     */
    public void switchContent(Fragment from, Fragment to, String tag) {
        if (from == null || to == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.frame_content, to, tag).commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
    }

    @Override
    public boolean enableToolbar() {
        return false;
    }
}
