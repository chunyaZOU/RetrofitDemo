package com.zcy.ygs.retrofitdemo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ygs on 2018/5/5.
 */

public class ServiceGenerator {
    private static final String BASE_URL="https://api.github.com/";
    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor();

    private static OkHttpClient.Builder builder
            = new OkHttpClient().newBuilder();

    private static Retrofit.Builder retrofirBulider
            =new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    private static Retrofit retrofit;


    public static <S> S createService(Class<S> clazz){
        if(!builder.interceptors().contains(logging)){
            builder.addInterceptor(logging);
            retrofit=retrofirBulider
                    .client(builder.build())
                    .build();
        }
        return retrofit.create(clazz);
    }
}