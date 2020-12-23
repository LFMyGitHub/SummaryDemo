package com.longf.lib_common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.longf.lib_api.config.API;

import static com.longf.lib_api.config.API.ScanModule.KEY_IS_CONTINUOUS;
import static com.longf.lib_api.config.API.ScanModule.KEY_IS_QR_CODE;
import static com.longf.lib_api.config.API.ScanModule.KEY_TITLE;

public class LinkUtils {
    public static void parse(Context context, String link, String title) {
        SharedPreferences mShared = context.getSharedPreferences(API.SPModule.SP_APP, 0);
        //设置为已登录状态
        mShared.edit().putBoolean(API.SPModule.IS_LOGIN, true).commit();
        if (!mShared.getBoolean(API.SPModule.IS_LOGIN, false)) {//未登录
        } else {//登录
            if (!TextUtils.isEmpty(link)) {
                if (link.startsWith("http://") || link.startsWith("https://")) {//跳转WebView

                } else {
                    link = link.trim();
                    if (link.startsWith("summary://proto")) {//定义跳转格式
                        int pos = link.indexOf("view=") + "view=".length();
                        String value = "";
                        int len = link.length();
                        if (len > pos) {
                            value = link.substring(pos, len);
                        }
                        Bundle bundle;
                        switch (value) {
                            case "MyScanIcon"://我的二维码界面
                                ARouter.getInstance().build("").navigation();
                                break;
                            case "MyScan"://扫码界面
                                bundle = new Bundle();
                                bundle.putString(KEY_TITLE, title);
                                bundle.putBoolean(KEY_IS_CONTINUOUS, false);
                                ARouter.getInstance().build("/common/ScanCaptureActivity").withBundle("bundle", bundle).navigation();
                                break;
                            default:
                                if(value.startsWith("MyScanCode")){
                                    String[] all = value.split("_");
                                    bundle = new Bundle();
                                    bundle.putString(KEY_TITLE, title);
                                    bundle.putBoolean(KEY_IS_QR_CODE, Boolean.valueOf(all[1]));
                                    ARouter.getInstance().build("/me/CodeActivity").withBundle("bundle", bundle).navigation();
                                }
                                break;
                        }
                    }else {
                        ToastUtils.showToast(link);
                    }
                }
            } else {
                ToastUtils.showToast(title);
            }
        }
    }
}
