package com.zcy.ygs.retrofitdemo;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by ygs on 2018/5/5.
 */

public class StringConverterFactory extends Converter.Factory {

    public static final StringConverterFactory INSTANCE=new StringConverterFactory();
    public static StringConverterFactory create(){
        return INSTANCE;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if(type==String.class){
            return StringConverter.INSTANCE;
        }
        return null;
    }
}
