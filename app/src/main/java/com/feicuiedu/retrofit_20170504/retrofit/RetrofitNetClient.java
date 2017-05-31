package com.feicuiedu.retrofit_20170504.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gqq on 2017/5/5.
 */

public class RetrofitNetClient {

    private static RetrofitNetClient mRetrofitNetClient;
    private final Retrofit mRetrofit;
    private RetrofitApi mRetrofitApi;

    private RetrofitNetClient(){

        // 创建日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 创建OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        // Retrofit的初始化
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")// 设置baseUrl
                .client(okHttpClient)// 设置OkHttpClient
                .addConverterFactory(GsonConverterFactory.create())// 添加GSON转换器
                .build();

        // 实现写到一个方法里面
//        RetrofitApi retrofitApi = mRetrofit.create(RetrofitApi.class);
    }

    public RetrofitApi getRetrofitApi(){

        // 得到一个已经实例化好的接口
        if (mRetrofitApi==null){
            mRetrofitApi = mRetrofit.create(RetrofitApi.class);
        }
        return mRetrofitApi;

//        RetrofitApi retrofitApi = mRetrofit.create(RetrofitApi.class);
//        return retrofitApi;
    }

    public static synchronized RetrofitNetClient getInstance(){
        if (mRetrofitNetClient==null){
            mRetrofitNetClient = new RetrofitNetClient();
        }
        return mRetrofitNetClient;
    }
}
