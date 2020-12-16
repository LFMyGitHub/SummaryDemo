package com.longf.module_me.model;

import android.content.Context;

import com.longf.lib_api.CommonService;
import com.longf.lib_api.RetrofitManager;
import com.longf.lib_api.dto.RespDTO;
import com.longf.lib_api.entity.MainMeEntity;
import com.longf.lib_api.entity.MainMeSettingEntity;
import com.longf.lib_api.http.RxAdapter;
import com.longf.lib_common.mvp.model.BaseModel;
import com.longf.module_me.contract.MainMeContract;

import io.reactivex.Observable;

public class MainMeModel extends BaseModel implements MainMeContract.Model {
    private CommonService mCommonService;

    public MainMeModel(Context context) {
        super(context);
        mCommonService = RetrofitManager.getInstance().getCommonService();
    }

    @Override
    public Observable<RespDTO<MainMeEntity>> getMainMeData(String uuid) {
        return mCommonService.getMainMeData(uuid)
                .compose(RxAdapter.<RespDTO<MainMeEntity>>bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.exceptionTransformer())
                .compose(RxAdapter.schedulersTransformer());
    }

    @Override
    public Observable<RespDTO<MainMeSettingEntity>> getMainMeSettingData(String uuid) {
        return mCommonService.getMainMeSettingData(uuid)
                .compose(RxAdapter.<RespDTO<MainMeSettingEntity>>bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.exceptionTransformer())
                .compose(RxAdapter.schedulersTransformer());
    }
}
