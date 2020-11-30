package com.longf.module_main.mvvm.model;

import android.app.Application;

import com.longf.lib_api.CommonService;
import com.longf.lib_api.RetrofitManager;
import com.longf.lib_api.dto.RespDTO;
import com.longf.lib_api.entity.AdsEntity;
import com.longf.lib_api.entity.AdsFromEntity;
import com.longf.lib_api.http.RxAdapter;
import com.longf.lib_common.mvvm.model.BaseModel;

import io.reactivex.Observable;

public class SplashModel extends BaseModel {
    private CommonService mCommonService;

    public SplashModel(Application application) {
        super(application);
        mCommonService = RetrofitManager.getInstance().getCommonService();
    }

    /**
     * 通过类型获取广告
     * @param type
     * @return
     */
    public Observable<RespDTO<AdsEntity>> getAdMsg(String type) {
        return mCommonService.getAdMsg(type)
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }

    /**
     * 获取广告开关
     * @return
     */
    public Observable<RespDTO<AdsFromEntity>> getAdFrom() {
        return mCommonService.getAdFroms()
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
