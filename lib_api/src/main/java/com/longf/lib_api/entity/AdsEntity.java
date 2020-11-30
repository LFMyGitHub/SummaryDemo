package com.longf.lib_api.entity;

import java.util.List;

/**
 * 广告数据
 */
public class AdsEntity {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 测试开屏广告
         * cover : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606716364128&di=d1babaf0a1fe39b0cd5dbbe66e37fbe7&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20170901%2Fa8cdd20f3be148fbb4c103bd205c1b57.jpeg
         * url : /
         * create_time : 1606719244
         */

        private String name;
        private String cover;
        private String url;
        private String create_time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
