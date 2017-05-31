package com.feicuiedu.retrofit_20170504.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by gqq on 2017/5/4.
 */

public abstract class UICallback implements Callback{

    // 后台线程去创建一个主线程的Handler,Looper.getMainLooper()得到主线程的Looper
    private Handler mHandler = new Handler(Looper.getMainLooper());

    // 当前是后台线程
    // 能不能提供两个可以运行在主线程的方法，将请求失败、成功返回给调用者

    @Override
    public void onFailure(final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 运行在主线程
                onUIFailure(call,e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 运行在主线程
                try {
                    onUIResponse(call,response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 必须让调用者实现的方法：可以处理失败事件
    public abstract void onUIFailure(Call call,IOException e);

    // 必须让调用者实现的方法：可以处理成功事件
    public abstract void onUIResponse(Call call,Response response)throws IOException;
}
