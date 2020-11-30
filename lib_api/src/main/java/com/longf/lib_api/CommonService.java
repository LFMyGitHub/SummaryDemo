package com.longf.lib_api;

import com.longf.lib_api.dto.RespDTO;
import com.longf.lib_api.entity.AdsEntity;
import com.longf.lib_api.entity.AdsFromEntity;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommonService {
    /**
     * 获取自己广告内容
     * @param type
     * @return
     */
    @POST("/ad/index")
    Observable<RespDTO<AdsEntity>> getAdMsg(@Query("pos") String type);

    /**
     * 获取广告开关--显示自己广告还是第三方广告
     * @return
     */
    @POST("/ad/froms")
    Observable<RespDTO<AdsFromEntity>> getAdFroms();
}
