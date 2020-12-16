package com.longf.module_main.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.longf.lib_api.config.API;
import com.longf.lib_api.dto.RespDTO;
import com.longf.lib_api.entity.AdsEntity;
import com.longf.lib_api.entity.AdsFromEntity;
import com.longf.lib_api.http.ExceptionHandler;
import com.longf.lib_common.event.SingleLiveEvent;
import com.longf.lib_common.mvvm.viewmodel.BaseViewModel;
import com.longf.lib_api.util.GsonUtils;
import com.longf.lib_common.util.ImageLoadUtils;
import com.longf.lib_common.util.SimulateNetAPI;
import com.longf.lib_common.util.ToastUtils;
import com.longf.lib_common.util.inter.ImageDownLoadCallBack;
import com.longf.module_main.mvvm.model.SplashModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SplashViewModel extends BaseViewModel<SplashModel> {
    public static String TAG = SplashViewModel.class.getSimpleName();
    private SingleLiveEvent<AdsFromEntity> mAdsFromSingleLiveEvent;
    private SingleLiveEvent<AdsEntity> mAdsSingleLiveEvent;
    private Application mApplication;

    public SplashViewModel(@NonNull Application application, SplashModel modle) {
        super(application, modle);
        this.mApplication = application;
    }

    /**
     * 广告开关
     */
    public void getAdsFrom() {
        mModle.getAdFrom().subscribe(new Observer<RespDTO<AdsFromEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(RespDTO<AdsFromEntity> adsEntityRespDTO) {
                if (API.APP_STATE == 2) {
                    String adsFromEntity = SimulateNetAPI.getOriginalFundData("json/AdsFromEntity.json", mApplication);
                    mAdsFromSingleLiveEvent.postValue(GsonUtils.gsonToBean(adsFromEntity, AdsFromEntity.class));
                } else {
                    if (adsEntityRespDTO.code == ExceptionHandler.APP_ERROR.SUCC2) {
                        mAdsFromSingleLiveEvent.postValue(adsEntityRespDTO.data);
                    } else {
                        ToastUtils.showToast(adsEntityRespDTO.msg);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (API.APP_STATE == 2) {
                    //测试状态模拟成功获取数据
                    String adsFromEntity = SimulateNetAPI.getOriginalFundData("json/AdsFromEntity.json", mApplication);
                    mAdsFromSingleLiveEvent.postValue(GsonUtils.gsonToBean(adsFromEntity, AdsFromEntity.class));
                }
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 自己后台广告
     */
    public void getAdMsg() {
        mModle.getAdMsg("15").subscribe(new Observer<RespDTO<AdsEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(RespDTO<AdsEntity> adsEntityRespDTO) {
                if (API.APP_STATE == 2) {
                    String adsEntity = SimulateNetAPI.getOriginalFundData("json/AdsEntity.json", mApplication);
                    final AdsEntity adsEntity1 = GsonUtils.gsonToBean(adsEntity, AdsEntity.class);
                    mAdsSingleLiveEvent.postValue(adsEntity1);
                    if (adsEntity1 != null && adsEntity1.getList().size() > 0
                            && !TextUtils.isEmpty(adsEntity1.getList().get(0).getCover())) {
                        //获取开屏广告缓存看是否需要下载
                        String splashCathe = mShared.getString(API.ADModule.ADS_DATA, "");
                        if(adsEntity1.getList().get(0).getCreate_time().equals(splashCathe)){
                            //后台时间戳与本地缓存一样不需要下载
                        }else {
                            ImageLoadUtils.saveBitmap(mApplication, adsEntity1.getList().get(0).getCover(),
                                    "splash", false, new ImageDownLoadCallBack() {
                                        @Override
                                        public void onDownLoadSuccess() {
                                            mEditor.putString(API.ADModule.ADS_DATA, adsEntity1.getList().get(0).getCreate_time()).commit();
                                        }

                                        @Override
                                        public void onDownLoadFailed() {
                                        }
                                    });
                        }
                    } else {
                        mEditor.putString(API.ADModule.ADS_DATA, "").commit();
                    }
                } else {
                    if (adsEntityRespDTO.code == ExceptionHandler.APP_ERROR.SUCC2) {
                    } else {
                        ToastUtils.showToast(adsEntityRespDTO.msg);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (API.APP_STATE == 2) {
                    //测试状态模拟成功获取数据
                    String adsEntity = SimulateNetAPI.getOriginalFundData("json/AdsEntity.json", mApplication);
                    final AdsEntity adsEntity1 = GsonUtils.gsonToBean(adsEntity, AdsEntity.class);
                    mAdsSingleLiveEvent.postValue(adsEntity1);
                    if (adsEntity1 != null && adsEntity1.getList().size() > 0 &&
                            !TextUtils.isEmpty(adsEntity1.getList().get(0).getCover())) {
                        //获取开屏广告缓存看是否需要下载
                        String splashCathe = mShared.getString(API.ADModule.ADS_DATA, "");
                        if(adsEntity1.getList().get(0).getCreate_time().equals(splashCathe)){
                            //后台时间戳与本地缓存一样不需要下载
                        }else {
                            ImageLoadUtils.saveBitmap(mApplication, adsEntity1.getList().get(0).getCover(),
                                    "splash", false, new ImageDownLoadCallBack() {
                                        @Override
                                        public void onDownLoadSuccess() {
                                            mEditor.putString(API.ADModule.ADS_DATA, adsEntity1.getList().get(0).getCreate_time()).commit();
                                        }

                                        @Override
                                        public void onDownLoadFailed() {
                                        }
                                    });
                        }
                    } else {
                        mEditor.putString(API.ADModule.ADS_DATA, "").commit();
                    }
                }
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public SingleLiveEvent<AdsFromEntity> getmAdsFromSingleLiveEvent() {
        return mAdsFromSingleLiveEvent = createLiveData(mAdsFromSingleLiveEvent);
    }

    public SingleLiveEvent<AdsEntity> getmAdsSingleLiveEvent() {
        return mAdsSingleLiveEvent = createLiveData(mAdsSingleLiveEvent);
    }
}
