package com.longf.module_main;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.longf.lib_api.config.API;
import com.longf.lib_api.entity.AdsEntity;
import com.longf.lib_api.entity.AdsFromEntity;
import com.longf.lib_common.mvvm.BaseMvvmActivity;
import com.longf.lib_common.util.FileUtils;
import com.longf.lib_common.util.SimulateNetAPI;
import com.longf.lib_common.util.TimeCount;
import com.longf.lib_common.util.ToastUtils;
import com.longf.module_main.mvvm.factory.MainViewModelFactory;
import com.longf.module_main.mvvm.viewmodel.SplashViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseMvvmActivity<ViewDataBinding, SplashViewModel> implements View.OnClickListener {
    private TextView mTextView;
    private ImageView mImageView;
    private TimeCount mTimeCount;
    //倒计时15s
    private int mDelayTime = 15000;
    //计时结束自动跳转
    private boolean mAutoJump = true;

    @Override
    public int onBindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public boolean enableToolbar() {
        return false;
    }

    @Override
    public void initView() {
        mImageView = findViewById(R.id.iv_splash);
        mTextView = findViewById(R.id.btn_splash_next);
        mTextView.setOnClickListener(this);
        //隐藏导航栏
        SimulateNetAPI.hideBottomUIMenu(this);

        mTimeCount = new TimeCount(mDelayTime, 1000);
        mTimeCount.setOnTimeCountListener(new TimeCount.OnTimeCountListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextView.setText("跳过(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                mTextView.setText("跳过(" + 0 + "s)");
                mTimeCount.cancel();
                if (mAutoJump) {
                    startMainActivity();
                }
            }
        });
    }

    @Override
    public void initData() {
        //获取广告开关
        mViewModel.getAdsFrom();
    }

    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public Class<SplashViewModel> onBindViewModel() {
        return SplashViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return MainViewModelFactory.getInstance(getApplication());
    }

    @Override
    public void initViewObservable() {
        mViewModel.getmAdsFromSingleLiveEvent().observe(this, new Observer<AdsFromEntity>() {
            @Override
            public void onChanged(@Nullable AdsFromEntity adsFromEntity) {
                if(API.ADModule.AD_TYPE_ISCLICK.equals(adsFromEntity.getFroms())){
                    RxPermissions rxPermissions = new RxPermissions(SplashActivity.this);
                    rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    if (aBoolean){
                                        //申请的权限全部允许
                                        //广告1
                                        mViewModel.getAdMsg();
                                        String splashCathe = mShared.getString(API.ADModule.ADS_DATA, "");
                                        if(!TextUtils.isEmpty(splashCathe)){
                                            //已下载过开屏广告并缓存
                                            String fileName = FileUtils.initPath() + "/pictures/splash.png";
                                            Glide.with(SplashActivity.this).load(fileName).into(mImageView);
                                        }
                                        mImageView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ToastUtils.showToast("点击开屏广告跳转");
                                            }
                                        });
                                        mTimeCount.start();
                                    }else{
                                        //只要有一个权限被拒绝，就会执行
                                        ToastUtils.showToast("请允许手机读写权限");
                                    }
                                }
                            });
                }else if(API.ADModule.AD_TYPE_XCYD.equals(adsFromEntity.getFroms())){
                    //广告2
                }
            }
        });

        mViewModel.getmAdsSingleLiveEvent().observe(this, new Observer<AdsEntity>() {
            @Override
            public void onChanged(AdsEntity adsEntity) {

            }
        });
    }

    @Override
    public int onBindVariableId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_splash_next){
            mAutoJump = false;
            mTimeCount.cancel();
            startMainActivity();
        }
    }
}
