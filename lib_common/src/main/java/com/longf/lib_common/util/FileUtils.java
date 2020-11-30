package com.longf.lib_common.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;

import com.longf.lib_api.config.API;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    private static final File PARENT_PATH = Environment.getExternalStorageDirectory();
    private static String STORAGE_PATH = "";
    private static String DST_FOLDER_NAME = API.FileModule.FILE_DOWN_PATH;

    public static String initPath() {
        if (STORAGE_PATH.equals("")) {
            STORAGE_PATH = PARENT_PATH.getAbsolutePath() + File.separator + DST_FOLDER_NAME;
            File f = new File(STORAGE_PATH);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return STORAGE_PATH;
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public static boolean copy(File source, File target) {
        boolean status = true;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return status;
    }


    /**
     * 把文件插入到系统图库
     * @param context
     * @param targetFile 要保存的照片文件
     * @param path  要保存的照片的路径地址
     */
    public static void addMediaStore(Context context, File targetFile, String path) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues newValues = new ContentValues(5);
        newValues.put(MediaStore.Images.Media.DISPLAY_NAME, targetFile.getName());
        newValues.put(MediaStore.Images.Media.DATA, targetFile.getPath());
        newValues.put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000);
        newValues.put(MediaStore.Images.Media.SIZE, targetFile.length());
        newValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, newValues);
        MediaScannerConnection.scanFile(context, new String[]{path}, null, null);//刷新相册
    }
}
