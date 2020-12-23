package com.longf.module_main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.longf.lib_api.config.API;
import com.longf.lib_common.mvp.BaseActivity;
import com.longf.lib_common.provider.IMainProvider;
import com.longf.lib_common.provider.IMeProvider;
import com.longf.lib_common.util.LinkUtils;
import com.longf.lib_common.util.ToastUtils;
import com.longf.module_main.entity.MainChannel;
import com.next.easynavigation.view.EasyNavigationBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {
    @Autowired(name = "/main/main")
    IMainProvider mIMainProvider;

    @Autowired(name = "/me/main")
    IMeProvider mIMeProvider;

    private Fragment mMainFragment;
    private Fragment mMeFragment;
    private Fragment mCurrFragment;

    private EasyNavigationBar mNnavigationBar;
    private String[] mTabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] mNormalIcon = {R.drawable.index, R.drawable.find, R.drawable.message, R.drawable.me};
    //选中时icon
    private int[] mSelectIcon = {R.drawable.index1, R.drawable.find1, R.drawable.message1, R.drawable.me1};
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    public int onBindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        if (mIMainProvider != null) {
            mMainFragment = mIMainProvider.getMainFragment();
        }
        if (mIMeProvider != null) {
            mMeFragment = mIMeProvider.getMainMeFragment();
        }
        mCurrFragment = mMainFragment;
        if (mMainFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mMainFragment, MainChannel.MAIN.name).commit();
        }

        if (API.MENU_STATE == 1) {
            useNavigationMenu();
        } else if (API.MENU_STATE == 2) {
            useEasyNavigationBar();
        } else if (API.MENU_STATE == 3) {

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

    @SuppressLint("RestrictedApi")
    private void useNavigationMenu() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        FloatingActionButton floatingActionButton = findViewById(R.id.navigation_center_fab);
        navigation.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
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
                } else if (i == R.id.navigation_center) {
                    //FloatingActionButton没有自定义点击事件时 会屏蔽点击事件
                    return false;
                }
                return false;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("中间按钮");
            }
        });
    }

    private void useEasyNavigationBar() {
        mNnavigationBar = findViewById(R.id.navigationBar);
        mNnavigationBar.setVisibility(View.VISIBLE);
        mFragments.add(mMainFragment);
        mFragments.add(mMainFragment);
        mFragments.add(mMainFragment);
        mFragments.add(mMeFragment);

        mNnavigationBar.titleItems(mTabText)
                .normalIconItems(mNormalIcon)
                .selectIconItems(mSelectIcon)
                .centerImageRes(R.drawable.common_cream)
                .mode(EasyNavigationBar.NavigationMode.MODE_ADD)
                .centerLayoutRule(EasyNavigationBar.RULE_BOTTOM)
//                .addCustomView(LayoutInflater.from(this).inflate(R.layout.main_center_cusview, null))//添加view
//                .mode(EasyNavigationBar.NavigationMode.MODE_ADD_VIEW)
                .scaleType(ImageView.ScaleType.FIT_XY)
                .build();

        mNnavigationBar.setOnTabClickListener(new EasyNavigationBar.OnTabClickListener() {
            @Override
            public boolean onTabSelectEvent(View view, int position) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                        switchContent(mCurrFragment, mFragments.get(position), MainChannel.MAIN.name);
                        break;
                    case 3:
                        switchContent(mCurrFragment, mFragments.get(position), MainChannel.ME.name);
                        break;
                }
                mCurrFragment = mFragments.get(position);
                return false;
            }

            @Override
            public boolean onTabReSelectEvent(View view, int position) {
                return false;
            }
        });

        mNnavigationBar.setOnTabLoadListener(new EasyNavigationBar.OnTabLoadListener() {
            @Override
            public void onTabLoadCompleteEvent() {
                mNnavigationBar.setMsgPointCount(1, 5);
                mNnavigationBar.setMsgPointCount(2, 2000);
                mNnavigationBar.setHintPoint(3, true);
            }
        });

        mNnavigationBar.setOnCenterTabClickListener(new EasyNavigationBar.OnCenterTabSelectListener() {
            @Override
            public boolean onCenterTabSelectEvent(View view) {
                RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
                rxPermissions.request(Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    LinkUtils.parse(MainActivity.this, "summary://proto?view=MyScan", "扫一扫");
                                } else {
                                    //只要有一个权限被拒绝，就会执行
                                    ToastUtils.showToast("请允许手机相机权限");
                                }
                            }
                        });
                return false;
            }
        });
    }
}
