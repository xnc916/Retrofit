package com.feicuiedu.retrofit_20170504.mvp;

import com.feicuiedu.retrofit_20170504.retrofit.Repo;
import com.feicuiedu.retrofit_20170504.retrofit.RepoResult;
import com.feicuiedu.retrofit_20170504.retrofit.RetrofitNetClient;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gqq on 2017/5/8.
 */

// 业务类：中间人：去拿到Model的数据，然后给View
public class MvpPresenter {

    /**
     * 是不是可以创建一个MvpActivity呢？直接调用里面的方法
     * 效果确实出来了，但是
     * 1. Activity是视图，目的是将视图和业务分离，而又创建Activity，违背了分离的初衷
     * 2. 业务是个耗时的操作，持有Activity的引用，请求结束前，Activity销毁了，Presenter一直持有Activity的对象
     *      会造成Activity无法被回收，会造成内存泄漏。
     *
     * 怎么样去与视图进行交互？(不创建Activity的情况下调用里面的视图方法)
     * 接口回调：
     * A接口 a();
     * Aimpl 实现a();
     * A a = new Aimpl();
     * a.a();
     *
     * 1. 创建一个视图接口：视图方法(显示进度、隐藏进度等)
     * 2. 让Activity实现这个视图接口，实现里面的视图方法
     * 3. 采用视图接口调用视图方法，就是调用的实现类(Activity里面具体实现的视图方法)
     *      视图接口怎么初始化呢？在构造方法中传过来一个已经实例化好的接口(接口实现类)
     *
     */
    private MvpView mMvpView;

    public MvpPresenter(MvpView mvpView) {
        mMvpView = mvpView;
    }

    // 去进行数据的获取(业务逻辑)
    public void getData(){

        // 涉及到视图的交互
        // 进度条的显示
        mMvpView.showProgress();

        Call<RepoResult> resultCall = RetrofitNetClient.getInstance().getRetrofitApi().getData("language:java",1);
        resultCall.enqueue(new Callback<RepoResult>() {

            // 请求成功
            @Override
            public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {

                // 进度条隐藏掉
                mMvpView.hideProgress();

                if (response.isSuccessful()){
                    RepoResult repoResult = response.body();
                    if (repoResult==null) {

                        // 视图上弹出一个吐司
                        mMvpView.showMessage("未知的错误");
                        return;

                    }
                    List<Repo> repoList = repoResult.getItems();
                    if (repoList!=null){
                        // 拿到数据了，将数据返回出去，给视图
                        mMvpView.setData(repoList);
                    }
                }

            }

            // 请求失败
            @Override
            public void onFailure(Call<RepoResult> call, Throwable t) {
                // 进度条隐藏
                mMvpView.hideProgress();
                // 显示一个吐司说明一下
                mMvpView.showMessage("请求失败："+t.getMessage());
            }
        });
    }
}
