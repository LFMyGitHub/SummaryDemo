package com.longf.lib_common.view.scan;

import android.widget.TextView;

import com.longf.lib_api.config.API;
import com.longf.lib_common.R;
import com.longf.lib_common.mvp.BaseActivity;

public class ScanResultActivity extends BaseActivity {
    private TextView mTvScanResult;

    @Override
    public int onBindLayout() {
        return R.layout.activity_scan_result;
    }

    @Override
    public void initView() {
        mTvScanResult = findViewById(R.id.tv_scan_result);
    }

    @Override
    public void initData() {
        mTvScanResult.setText(getIntent().getStringExtra(API.ScanModule.QRCODE_VALUE));
    }
}
