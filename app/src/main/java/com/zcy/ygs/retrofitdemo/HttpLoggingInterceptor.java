package com.zcy.ygs.retrofitdemo;

import android.util.Log;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ygs on 2018/5/5.
 */

public class HttpLoggingInterceptor implements Interceptor {
    public static final String REQUEST_LOG="request-log";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Log.i(REQUEST_LOG,request.toString());

        return chain.proceed(request);
    }
}
