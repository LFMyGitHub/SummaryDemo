package com.longf.lib_api.dto;

import java.io.Serializable;

public class RespDTO<T> implements Serializable {

    public int code ;
    public String msg = "";
    public String token = "";
    public T data;

    @Override
    public String toString() {
        return "RespDTO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                ", data=" + data +
                '}';
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTokeng(String token) {
        this.token = token;
    }
}
