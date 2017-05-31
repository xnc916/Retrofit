package com.feicuiedu.retrofit_20170504.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.feicuiedu.retrofit_20170504.R;
import com.feicuiedu.retrofit_20170504.okhttp.MultUser;
import com.feicuiedu.retrofit_20170504.okhttp.User;
import com.feicuiedu.retrofit_20170504.okhttp.UserResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }

    // GET 请求
    @OnClick(R.id.btnRetrofit_get)
    public void getData(){
//        // 执行Retrofit构建的请求
//        Call<ResponseBody> call = RetrofitNetClient.getInstance().getRetrofitApi().getData("language:java",1);
//        call.enqueue(new Callback<ResponseBody>() {
//
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(RetrofitActivity.this, "请求成功："+response.code(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(RetrofitActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
//            }
//        });

        // 使用转换器之后的使用
        RetrofitNetClient.getInstance().getRetrofitApi().getData("language:java",1).enqueue(new Callback<RepoResult>() {
            @Override
            public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
                // 请求成功
                if (response.isSuccessful()){
                    RepoResult repoResult = response.body();// 添加转换器之后自动完成了解析，拿到了实体类
                    if (repoResult==null){
                        Toast.makeText(RetrofitActivity.this, "未知的错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<Repo> repoList = repoResult.getItems();
                    Toast.makeText(RetrofitActivity.this, "数据："+repoList.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RepoResult> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // POST请求
    @OnClick(R.id.btnRetrofit_post)
    public void register(){

        final User user = new User("123456","123456");

        final RequestBody requestBody = RequestBody.create(null,new Gson().toJson(user));

        RetrofitNetClient.getInstance().getRetrofitApi().register(requestBody).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                // 是不是成功
                if (response.isSuccessful()){
                    try {
                        String json = response.body().string();

                        UserResult userResult = new Gson().fromJson(json, UserResult.class);

                        if (userResult==null){
                            Toast.makeText(RetrofitActivity.this, "未知的错误", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(RetrofitActivity.this, userResult.getMsg(), Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

//                Toast.makeText(RetrofitActivity.this, "请求成功："+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 表单
    @OnClick(R.id.btnRetrofit_form)
    public void getForm(){
        RetrofitNetClient.getInstance().getRetrofitApi().getForm("123456","123456").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(RetrofitActivity.this, "请求成功"+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 多部分
    @OnClick(R.id.btnRetrofit_mult)
    public void getMult(){

        MultUser multUser = new MultUser(
                "yt59856b15cf394e7b84a7d48447d16098",
                "xc62",
                "555",
                "123456",
                "0F8EC12223174657B2E842076D54C361"
        );

//        RequestBody requestBody = RequestBody.create(null,new Gson().toJson(multUser));
//
//        RetrofitNetClient.getInstance().getRetrofitApi().getMult(requestBody).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(RetrofitActivity.this, "请求成功"+response.code(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(RetrofitActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        // 构建多部分请求的某一个部分part
        MultipartBody.Part part = MultipartBody.Part.create(RequestBody.create(null,new Gson().toJson(multUser)));

        RetrofitNetClient.getInstance().getRetrofitApi().getMult1(part).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(RetrofitActivity.this, "请求成功"+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "请求失败", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
