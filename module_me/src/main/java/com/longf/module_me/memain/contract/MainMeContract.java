package com.longf.module_me.memain.contract;

import com.longf.lib_api.dto.RespDTO;
import com.longf.lib_api.entity.MainMeEntity;
import com.longf.lib_api.entity.MainMeSettingEntity;
import com.longf.lib_common.mvp.view.BaseRefreshView;

import io.reactivex.Observable;

public interface MainMeContract {
    interface Presenter{
    }
    interface View<MainMeEntity> extends BaseRefreshView<MainMeEntity> {
        void refreshData(MainMeEntity mainMeEntity);

        void loadList(MainMeSettingEntity mainMeSettingEntity);
    }
    interface Model{
        Observable<RespDTO<MainMeEntity>> getMainMeData(String uuid);
        Observable<RespDTO<MainMeSettingEntity>> getMainMeSettingData(String uuid);
    }
}
