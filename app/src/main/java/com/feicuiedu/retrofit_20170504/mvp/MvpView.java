package com.feicuiedu.retrofit_20170504.mvp;

import com.feicuiedu.retrofit_20170504.retrofit.Repo;

import java.util.List;

/**
 * Created by gqq on 2017/5/8.
 */

// 视图接口
public interface MvpView {

    void showProgress();// 显示进度

    void hideProgress();// 隐藏进度

    void showMessage(String msg);// 显示信息

    void setData(List<Repo> list);// 设置数据，在视图上展示

}
