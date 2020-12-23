package com.longf.module_me.scan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.zxing.BarcodeFormat;
import com.king.zxing.util.CodeUtils;
import com.longf.lib_api.config.API;
import com.longf.lib_common.mvp.BaseActivity;
import com.longf.module_me.R;

@Route(path = "/me/CodeActivity")
public class CodeActivity extends BaseActivity {
    //默认为条形码
    private boolean mIsQRCode = false;
    private ImageView mIvCode;

    @Override
    public int onBindLayout() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initView() {
        mIvCode = findViewById(R.id.ivCode);
    }

    @Override
    public void initData() {
        if(getIntent() != null && getIntent().getBundleExtra("bundle") != null){
            mIsQRCode = getIntent().getBundleExtra("bundle").getBoolean(API.ScanModule.KEY_IS_QR_CODE,false);
        }
        if(mIsQRCode){
            createQRCode(getString(R.string.app_name));
        }else{
            createBarCode("1234567890");
        }
    }

    @Override
    public String getTootBarTitle() {
        if(getIntent() != null && getIntent().getBundleExtra("bundle") != null){
            return getIntent().getBundleExtra("bundle").getString(API.ScanModule.KEY_TITLE,"我的二维码");
        }
        return "我的二维码";
    }

    /**
     * 生成二维码
     * @param content
     */
    private void createQRCode(final String content){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //生成二维码相关放在子线程里面
                        Bitmap logo = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
                        final Bitmap bitmap =  CodeUtils.createQRCode(content,600,logo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //显示二维码
                                mIvCode.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
        ).start();
    }

    /**
     * 生成条形码
     * @param content
     */
    private void createBarCode(final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //生成条形码相关放在子线程里面
                final Bitmap bitmap = CodeUtils.createBarCode(content, BarcodeFormat.CODE_128,800,200,null,true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //显示条形码
                        mIvCode.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }
}
