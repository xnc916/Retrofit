package com.feicuiedu.retrofit_20170504.okhttp;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by gqq on 2017/5/4.
 */

// 单例OkHttpClient
public class OkHttpNetClient {

    // 单例：饿汉式、懒汉式
    private static OkHttpNetClient mOkHttpNetClient;
    private final OkHttpClient mOkHttpClient;
    //    private OkHttpNetClient mOkHttpNetClient = new OkHttpNetClient();// 饿汉式

    // 私有的构造方法
    private OkHttpNetClient(){

        // 日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 完成OkHttpClient的创建
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

//        // 创建
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com")
//                // 必须要添加的，在请求构建的时候，可以根据baseurl来进行路径的拼接(请求构建写的是相对的路径)
//                // 如果在请求构建的时候传入的就是一个完整的路径，不会进行拼接
//                .client(mOkHttpClient)// 设置OkHttpClient给Retrofit，可以使用OkHttpClient的一些功能
//                .build();

    }

    // 共有的创建方法，为了保证懒汉式的线程安全
    public static synchronized OkHttpNetClient getInstance(){
        if (mOkHttpNetClient==null){
            mOkHttpNetClient = new OkHttpNetClient();
        }
        return mOkHttpNetClient;
    }

    // 构建post请求
    public Call register(User user){

        // 实体类转换为Json字符串
        // GSON：Json字符串<-->实体类
        Gson gson = new Gson();

        // 将实体类转换为Json字符串
        String json = gson.toJson(user);

        // 请求体的构建
        RequestBody requestBody = RequestBody.create(null,json);

        Request request = new Request.Builder()
                .post(requestBody)
                .url("http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=register")
                .build();
        return mOkHttpClient.newCall(request);
    }

    // 构建表单请求：键值对形式提交
    public Call postForm(String name,String password){

        // 构建表单的请求体:添加键值对数据
        RequestBody requestBody = new FormBody.Builder()
                .add("username",name)// 添加key-value的键值对，上传的数据
                .add("password",password)
                .build();

        // 构建请求
        Request request = new Request.Builder()
                .post(requestBody)
                .url("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=register")
                .build();

        return mOkHttpClient.newCall(request);
    }

    // 多部分请求的构建：包括多个部分(每一个部分对应一个单独的请求体)，最终多个部分构建到一个请求体里面上传到服务器
    // 将多个部分的请求构建到一个多部分请求体
    public Call postMult(MultUser multUser){

        // 多部分的请求体
        RequestBody requestBody = new MultipartBody.Builder()
                // 添加需要上传的某一部分
                 .addPart(RequestBody.create(null,new Gson().toJson(multUser)))
//                .addPart(其他的部分)// 可以添加多个部分的
                .build();

        //构建请求
        Request request = new Request.Builder()
                .post(requestBody)
                .url("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=update")
                .build();
        return mOkHttpClient.newCall(request);
    }
}
