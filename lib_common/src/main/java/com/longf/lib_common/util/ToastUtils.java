package com.longf.lib_common.util;

import android.widget.Toast;

import com.longf.lib_common.BaseApplication;

public class ToastUtils {
    public static void showToast(String message) {
        Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resid) {
        Toast.makeText(BaseApplication.getInstance(), BaseApplication.getInstance().getString(resid), Toast.LENGTH_SHORT)
                .show();
    }
}
