package com.longf.lib_api.config;

public class API {
    //1:开发2:测试
    public static final int APP_STATE = 2;
    //菜单 1:BottomNavigationView 2:EasyNavigationBar 3:
    public static final int MENU_STATE = 2;

    public static final String URL_HOST_BASE = "https://uc-csga.xcydhn.com";
    public static final String URL_HOST_NEWS = "http://192.168.31.105:8767";

    //广告配置
    public static class ADModule{
        public static final String AD_TYPE_ISCLICK = "iclick";
        public static final String AD_TYPE_XCYD = "xcyd";

        public static final String ADS_DATA = "ads_data";
    }

    //文件配置
    public static class FileModule{
        public static final String FILE_DOWN_PATH = "MyDown";
    }

    //缓存配置
    public static class SPModule{
        public static final String SP_APP = "sp_app";
        public static final String IS_LOGIN = "is_login";
    }

    //扫码配置
    public static class ScanModule{
        //第三方调用识别
        public static final String SCAN_EXTERNAL_CALL = "scan_external_call";
        //扫码第三方回调数据
        public static final String QRCODE_VALUE = "qrcode_value";
        //第三方调用扫码功能回调值
        public final static String QRCODE_SOURCE = "qrcode_source";
        //扫码界面title
        public static final String KEY_TITLE = "key_title";
        //是否连续扫码
        public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";
        //条形码或二维码
        public static final String KEY_IS_QR_CODE = "key_is_qr_code";
    }
}
