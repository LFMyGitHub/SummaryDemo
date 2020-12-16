package com.longf.lib_api.http;

import android.net.ParseException;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.longf.lib_api.RetrofitManager;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

public class ExceptionHandler {
    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, SYSTEM_ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case SYSTEM_ERROR.UNAUTHORIZED:
                    ex.message = "操作未授权";
                    ex.code = SYSTEM_ERROR.UNAUTHORIZED;
                    Toast.makeText(RetrofitManager.mContext,"您的登录已失效,请重新登录",Toast.LENGTH_SHORT).show();
                    break;
                case SYSTEM_ERROR.FORBIDDEN:
                    ex.message = "请求被拒绝";
                    break;
                case SYSTEM_ERROR.NOT_FOUND:
                    ex.message = "资源不存在";
                    break;
                case SYSTEM_ERROR.REQUEST_TIMEOUT:
                    ex.message = "服务器执行超时";
                    break;
                case SYSTEM_ERROR.INTERNAL_SERVER_ERROR:
                    ex.message = "服务器内部错误";
                    break;
                case SYSTEM_ERROR.SERVICE_UNAVAILABLE:
                    ex.message = "服务器不可用";
                    break;
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException || e instanceof MalformedJsonException) {
            ex = new ResponseThrowable(e, SYSTEM_ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, SYSTEM_ERROR.NETWORD_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLException) {
            ex = new ResponseThrowable(e, SYSTEM_ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponseThrowable(e, SYSTEM_ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseThrowable(e, SYSTEM_ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.UnknownHostException) {
            ex = new ResponseThrowable(e, SYSTEM_ERROR.TIMEOUT_ERROR);
            ex.message = "主机地址未知";
            return ex;
        } else {
            ex = new ResponseThrowable(e, SYSTEM_ERROR.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }

    }

    public class SYSTEM_ERROR {
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int REQUEST_TIMEOUT = 408;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int SERVICE_UNAVAILABLE = 503;

        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         SSL_ERROR         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        /**
         * 自定义错误
         */
        public static final int DEFAULT_ERROR = 1007;

    }

    public interface APP_ERROR {
        public static final int SUCC1 = 0;//	处理成功，无错误
        public static final int SUCC2 = 200;//	处理成功，无错误
        public static final int INTERFACE_PROCESSING_TIMEOUT = 1;//	接口处理超时
        public static final int INTERFACE_INTERNAL_ERROR = 2;//	接口内部错误
        public static final int PARAMETERS_EMPTY = 3;//	必需的参数为空
        public static final int AUTHENTICATION_FAILED = 4;//	鉴权失败，用户没有使用该项功能（服务）的权限。
        public static final int PARAMETERS_ERROR = 5;//	参数错误
    }
}
