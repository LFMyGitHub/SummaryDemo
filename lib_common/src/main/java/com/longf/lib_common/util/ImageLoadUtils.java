package com.longf.lib_common.util;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.longf.lib_common.util.inter.ImageDownLoadCallBack;

import java.io.File;

public class ImageLoadUtils {
    /**
     * 保存图片到本地
     *
     * @param context
     * @param imgPathStart 图片url
     * @param imgPathEnd   保存图片到本地名称
     * @param isReshPhotos 是否刷新相册
     */
    public static void saveBitmap(final Context context, String imgPathStart, final String imgPathEnd,
                                  final boolean isReshPhotos, final ImageDownLoadCallBack callBack) {
        Glide.with(context).downloadOnly().load(imgPathStart).into(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                if (resource != null) {
                    try {
                        //获取到下载得到的图片，进行本地保存
                        String folder = FileUtils.initPath();
                        File appDir = new File(folder, "pictures");
                        if (!appDir.exists()) {
                            appDir.mkdirs();
                        }
                        String fileName = imgPathEnd + ".png";
                        File destFile = new File(appDir, fileName);
                        boolean status = FileUtils.copy(resource, destFile);
                        if (status) {
                            if (isReshPhotos) {
                                FileUtils.addMediaStore(context, destFile, destFile.getAbsolutePath());
                            }
                            ToastUtils.showToast("下载成功");
                            callBack.onDownLoadSuccess();
                        } else {
                            ToastUtils.showToast("下载失败");
                            callBack.onDownLoadFailed();
                        }
                    } catch (Exception e) {
                        Log.i("ImageLoadUtils", e.toString());
                        ToastUtils.showToast("下载失败");
                        callBack.onDownLoadFailed();
                    }
                }
            }
        });
    }
}
