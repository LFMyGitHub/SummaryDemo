package com.longf.lib_common.view.scan;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.zxing.CaptureActivity;
import com.king.zxing.CaptureHelper;
import com.king.zxing.camera.FrontLightMode;
import com.king.zxing.util.CodeUtils;
import com.longf.lib_api.config.API;
import com.longf.lib_common.R;
import com.longf.lib_common.util.SimulateNetAPI;
import com.longf.lib_common.util.ToastUtils;
import com.longf.lib_common.util.UriUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

import static com.longf.lib_api.config.API.ScanModule.KEY_TITLE;
import static com.longf.lib_api.config.API.ScanModule.QRCODE_SOURCE;

/**
 * 扫描条形码和二维码
 */
@Route(path = "/common/ScanCaptureActivity")
public class ScanCaptureActivity extends CaptureActivity implements View.OnClickListener {
    //判断是否是第三方调用
    private String mQrSource;
    private CaptureHelper mCaptureHelper;
    private ImageView mIvGallery;
    private ImageView mIvLeft;
    private TextView mTvTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_capture_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar_root);
        SimulateNetAPI.immersiveStatusBar(this, toolbar, 0.2f);
        mTvTitle = findViewById(R.id.toolbar_title);
        mIvLeft = findViewById(R.id.ivLeft);
        if(getIntent() != null && getIntent().getBundleExtra("bundle") != null){
            if(TextUtils.isEmpty(getIntent().getBundleExtra("bundle").getString(KEY_TITLE))){
                mTvTitle.setText("扫一扫");
            }else {
                mTvTitle.setText(getIntent().getBundleExtra("bundle").getString(KEY_TITLE));
            }
            if(!TextUtils.isEmpty(getIntent().getBundleExtra("bundle").getString(QRCODE_SOURCE))){
                mQrSource = getIntent().getStringExtra(QRCODE_SOURCE);
            }
        }
        mIvGallery = findViewById(R.id.iv_gallery);
        mIvGallery.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
        //获取CaptureHelper，里面有扫码相关的配置设置
        mCaptureHelper = getCaptureHelper();
        mCaptureHelper.playBeep(false)//播放音效
                .vibrate(true)//震动
                .supportVerticalCode(true)//支持扫垂直条码，建议有此需求时才使用。
                .frontLightMode(FrontLightMode.AUTO)//设置闪光灯模式
                .tooDarkLux(45f)//设置光线太暗时，自动触发开启闪光灯的照度值
                .brightEnoughLux(100f)//设置光线足够明亮时，自动触发关闭闪光灯的照度值
                .continuousScan(false)//是否连扫
                .supportLuminanceInvert(true);//是否支持识别反色码（黑白反色的码），增加识别率
    }

    @Override
    public int getIvTorchId() {
        return super.getIvTorchId();
    }

    /**
     * 接收扫描结果回调
     *
     * @param result
     * @return
     */
    @Override
    public boolean onResultCallback(String result) {
        if (API.ScanModule.SCAN_EXTERNAL_CALL.equals(mQrSource)) {//第三方调用
            Intent intent = new Intent();
            intent.putExtra(API.ScanModule.QRCODE_VALUE, result);
            setResult(200, intent);
            finish();
        } else {//非三方调用
            handleScan(result);
        }
        return true;
    }

    /**
     * 应用内部扫码结果处理
     *
     * @param result
     */
    private void handleScan(String result) {
        Intent intent = new Intent(this, ScanResultActivity.class);
        intent.putExtra(API.ScanModule.QRCODE_VALUE, result);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_gallery) {
            RxPermissions rxPermissions = new RxPermissions(ScanCaptureActivity.this);
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(pickIntent, 1001);
                            } else {
                                //只要有一个权限被拒绝，就会执行
                                ToastUtils.showToast("请允许手机相册权限");
                            }
                        }
                    });
        } else if (id == R.id.ivLeft) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1001:
                    parsePhoto(data);
                    break;
            }
        }
    }

    private void parsePhoto(Intent data) {
        final String path = UriUtils.getImagePath(this, data);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        //异步解析
        asyncThread(new Runnable() {
            @Override
            public void run() {
                final String result = CodeUtils.parseCode(path);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleScan(result);
                    }
                });
            }
        });
    }

    private void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}
