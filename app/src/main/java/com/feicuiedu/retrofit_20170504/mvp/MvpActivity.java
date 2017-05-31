package com.feicuiedu.retrofit_20170504.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.feicuiedu.retrofit_20170504.R;
import com.feicuiedu.retrofit_20170504.retrofit.Repo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvpActivity extends AppCompatActivity implements MvpView{

    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.btnAdd)
    Button mBtnAdd;
    private ArrayAdapter<String> mAdapter;

    /**
     * 需求：
     * 点击Button，显示进度，加载数据，加载完，进度隐藏，展示给ListView
     * <p>
     * 视图逻辑：(一般就是业务逻辑过程中的视图交互)
     * 1. Button点击事件
     * 2. 进度条的显示和隐藏
     * 3. 拿到的数据展示给ListView
     * 4. 显示一个吐司
     * <p>
     * 业务逻辑：
     * 根据请求接口，加载数据，最终是将数据给视图
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);

        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);
        
    }

    @OnClick(R.id.btnAdd)
    public void onClick() {
        // 去执行业务逻辑：通知Presenter去执行
        new MvpPresenter(this).getData();
    }


    //-------------------------视图处理------------------------
    // 显示进度
    @Override
    public void showProgress(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    // 隐藏进度
    @Override
    public void hideProgress(){
        mProgressBar.setVisibility(View.GONE);
    }

    // 设置数据
    @Override
    public void setData(List<Repo> list){
        for (Repo repo :
                list) {
            mAdapter.add(repo.getFullName());
        }
        mAdapter.notifyDataSetChanged();
    }

    // 显示吐司
    @Override
    public void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
