package com.longf.lib_api.entity;

import java.util.List;

public class MainMeSettingEntity {

    private List<List<DataBean>> list;

    public List<List<DataBean>> getList() {
        return list;
    }

    public void setList(List<List<DataBean>> list) {
        this.list = list;
    }

    public static class DataBean {
        /**
         * id : 84
         * ico : https://res-csga.xcydhn.com/edd759574583b303ca74bc78ae036e98.png
         * title : 我的商铺
         * url : https://pasph5-csga.xcydhn.com/#/myinfo
         * cate_id : 4
         * group_id: 1
         */

        private String id;
        private String ico;
        private String title;
        private String url;
        private String cate_id;
        private int group_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }
    }
}
