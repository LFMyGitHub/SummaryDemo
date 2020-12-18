package com.longf.module_me.presenter;

import android.content.Context;

import com.longf.lib_api.config.API;
import com.longf.lib_api.dto.RespDTO;
import com.longf.lib_api.entity.MainMeEntity;
import com.longf.lib_api.entity.MainMeSettingEntity;
import com.longf.lib_common.mvp.presenter.BaseRefreshPresenter;
import com.longf.lib_api.util.GsonUtils;
import com.longf.lib_common.util.NetUtils;
import com.longf.lib_common.util.SimulateNetAPI;
import com.longf.lib_common.util.ToastUtils;
import com.longf.module_me.contract.MainMeContract;
import com.longf.module_me.model.MainMeModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainMePresenter extends BaseRefreshPresenter<MainMeModel, MainMeContract.View<MainMeEntity>,
        MainMeEntity> implements MainMeContract.Presenter {
    private Context mContext;

    public MainMePresenter(Context context, MainMeContract.View<MainMeEntity> view, MainMeModel model) {
        super(context, view, model);
        this.mContext = context;
    }

    @Override
    public void refreshData() {
    }

    @Override
    public void loadMoreData() {

    }

    public void getMainMeData(){
        mView.hideNoDataView();
        if (!NetUtils.checkNetToast()) {
            mView.showNetWorkErrView();
            return;
        }
        mModel.getMainMeData("9a485d94-ffda-4a68-997c-c5ecdb1ebe74").subscribe(new Observer<RespDTO<MainMeEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mView.showInitLoadView();
            }

            @Override
            public void onNext(RespDTO<MainMeEntity> meEntityRespDTO) {
                MainMeEntity meEntity = meEntityRespDTO.data;
                if (meEntity != null) {
                    mView.refreshData(meEntity);
                } else {
                    mView.showNoDataView();
                }
                mView.stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                mView.hideInitLoadView();
            }

            @Override
            public void onComplete() {
                mView.hideInitLoadView();
            }
        });
    }


    public void getMainMeSettingData(){
        mView.hideNoDataView();
        if (!NetUtils.checkNetToast()) {
            mView.showNetWorkErrView();
            return;
        }
        mModel.getMainMeSettingData("9a485d94-ffda-4a68-997c-c5ecdb1ebe74").subscribe(new Observer<RespDTO<MainMeSettingEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mView.showInitLoadView();
            }

            @Override
            public void onNext(RespDTO<MainMeSettingEntity> meSettingEntityRespDTO) {
                if (API.APP_STATE == 2) {
                    String meSettingEntity = SimulateNetAPI.getOriginalFundData("json/MainMeSettingEntity.json", mContext);
                    final MainMeSettingEntity meSettingEntity1 = GsonUtils.gsonToBean(meSettingEntity, MainMeSettingEntity.class);
                    mView.loadList(meSettingEntity1);
                } else {
                }
                mView.stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                if (API.APP_STATE == 2) {
                    String meSettingEntity = SimulateNetAPI.getOriginalFundData("json/MainMeSettingEntity.json", mContext);
                    final MainMeSettingEntity meSettingEntity1 = GsonUtils.gsonToBean(meSettingEntity, MainMeSettingEntity.class);
                    mView.loadList(meSettingEntity1);
                }
                mView.hideInitLoadView();
            }

            @Override
            public void onComplete() {
                mView.hideInitLoadView();
            }
        });
    }
}
