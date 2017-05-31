package com.feicuiedu.retrofit_20170504;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.feicuiedu.retrofit_20170504.mvp.MvpActivity;
import com.feicuiedu.retrofit_20170504.okhttp.MultUser;
import com.feicuiedu.retrofit_20170504.okhttp.OkHttpNetClient;
import com.feicuiedu.retrofit_20170504.okhttp.UICallback;
import com.feicuiedu.retrofit_20170504.okhttp.User;
import com.feicuiedu.retrofit_20170504.okhttp.UserResult;
import com.feicuiedu.retrofit_20170504.retrofit.RetrofitActivity;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    // ctrl+shift+-/+ 展开和收起方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnOkHttp_get)
    public void okHttpGet() {

        /**
         * 客户端
         * 1. 创建一个客户端：发送请求
         * 2. 构建请求的消息：
         *      请求方式
         *      请求的url
         *      请求头信息
         *      请求体信息
         * 3. 利用客户端将请求发送出去
         *
         * 服务端返回的数据，客户端怎么处理
         * 1. 拿到响应码、响应头信息、响应体等
         * 2. 判断：判断是不是真正的成功了，如果成功，可以拿到响应体数据
         *          解析等，拿到最终的集合或实体类
         */

        // 日志拦截器：设置给OkHttpClient
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // 设置拦截的级别:默认是NONE，没有信息
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 创建客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)//将拦截器设置给OkHttpClient
//                .addInterceptor(自定义的拦截器等)
                .build();

        // 构建GET请求
        Request request = new Request.Builder()
                .get()// 请求方式
                .url("https://api.github.com/search/repositories?q=language:java&page=1")// 请求地址
//                .header("X-type","json")// 请求头信息，根据接口文档添加
                // GET请求不需要添加请求体
                .build();

        // 请求的发送
        okHttpClient.newCall(request).enqueue(new Callback() {

            // 请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                // 超时或者网络未连接
                // 后台线程，不可以做UI操作
                Log.i("TAG", "请求失败");
            }

            // 请求成功：1XX-5XX 走这个方法，所以一般会再做一个判断
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 响应码在2XX的时候才是真正的成功了
//                response// 响应消息：响应码等

//                response.code();// 响应码
//                response.header("");// 响应头信息
//                ResponseBody responseBody = response.body();// 响应体信息

                // 可以根据isSuccessful判断是不是真正的成功了
                if (response.isSuccessful()) {

                    ResponseBody body = response.body();
                    Log.i("TAG", "响应的字符串：" + body.string());

                }
                Log.i("TAG", "响应码：" + response.code());
            }
        });

        //        // 取消：任意线程取消，节省用户流量
//        call.cancel();
//
//        // 获取正在执行的所有请求的Call模型，遍历取消，直接请求失败
//        List<Call> calls = okHttpClient.dispatcher().runningCalls();
//        // 获取等待执行的所有的Call模型，遍历取消，直接就请求失败
//        List<Call> callList = okHttpClient.dispatcher().queuedCalls();
    }

    @OnClick(R.id.btnOkHttp_post)
    public void okHttpPost() {

        final User user = new User("123456", "123456");

        // 调用优化后的请求方法
        Call call = OkHttpNetClient.getInstance().register(user);
        // 异步回调
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("TAG", "请求成功");
                // 拿到结果解析
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    // 利用GSON来解析
                    UserResult userResult = new Gson().fromJson(json, UserResult.class);
                    if (userResult == null) {
                        Log.i("TAG", "发生了未知的错误");
                        return;
                    }
                    Log.i("TAG", "数据" + userResult.getMsg());
                }
            }
        });
    }

    // 表单和多部分
    @OnClick({R.id.btnOkHttp_form, R.id.btnOkHttp_mult,R.id.btnUI})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOkHttp_form:

                OkHttpNetClient.getInstance().postForm("123456","123456").enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("TAG","请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("TAG","请求成功："+response.body().string());

                        // 1. 做UI的操作
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
//                            }
//                        });

                        // 2. handler.post
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                // UI
//                                Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
                    }
                });

                break;
            case R.id.btnOkHttp_mult:

                // 需要上传的数据的实体类
                MultUser multUser = new MultUser("yt59856b15cf394e7b84a7d48447d16098",
                    "xc62",
                    "555",
                    "123456",
                    "0F8EC12223174657B2E842076D54C361"
                );

                OkHttpNetClient.getInstance().postMult(multUser).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("TAG","请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("TAG","请求成功"+response.body().string());
                    }
                });

                break;

            case R.id.btnUI:

                MultUser multUser1 = new MultUser("yt59856b15cf394e7b84a7d48447d16098",
                        "xc62",
                        "555",
                        "123456",
                        "0F8EC12223174657B2E842076D54C361"
                );

                OkHttpNetClient.getInstance().postMult(multUser1).enqueue(new UICallback() {
                    @Override
                    public void onUIFailure(Call call, IOException e) {
                        Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUIResponse(Call call, Response response) throws IOException {
                        Toast.makeText(MainActivity.this, "请求成功："+response.body().string(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }

    @OnClick(R.id.btnRetrofit)
    public void gotoRetrofit(){
        startActivity(new Intent(this, RetrofitActivity.class));
    }

    @OnClick(R.id.btnmvp)
    public void gotomvp(){
        startActivity(new Intent(this, MvpActivity.class));
    }
}
