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

    public static class FileModule{
        public static final String FILE_DOWN_PATH = "MyDown";
    }

    public static class SPModule{
        public static final String SP_APP = "sp_app";
    }
}
