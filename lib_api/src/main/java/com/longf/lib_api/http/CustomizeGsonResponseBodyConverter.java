package com.longf.lib_api.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.longf.lib_api.config.API;
import com.longf.lib_api.dto.RespDTO;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;

/**
 * @param <T>
 * @author lqx
 */
public class CustomizeGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomizeGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //把responsebody转为string
        String response = value.string();
        if (API.APP_STATE == 2) {
            //打印后台数据
            Log.e("response", response);
        }
        RespDTO respDTO = gson.fromJson(response, RespDTO.class);
        try {
            return (T) respDTO ;
        } finally {
            value.close();
        }
    }
}
