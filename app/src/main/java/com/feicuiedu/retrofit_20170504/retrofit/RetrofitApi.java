package com.feicuiedu.retrofit_20170504.retrofit;

import com.feicuiedu.retrofit_20170504.okhttp.User;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by gqq on 2017/5/5.
 */

//Retrofit 的接口构建
public interface RetrofitApi {

    /**
     * 注解的说明
     * HTTP 方面的：消息结构
     * 1. 请求方式：@GET("")、@POST("")
     * 2. url：单独说明
     * 3. 请求头信息
     *      @Headers({"X-type:aa","Accept-type:en"}) 在方法上添加固定的头信息
     *      @Header("") String string  动态修改某一个头信息，方法的参数中
     *      @HeaderMap 利用Map添加多个
     * 4. 请求体
     *      @Body 上传的请求体，只能有一个
     *
     * Url处理
     * 1. 查询的参数：一般运用在GET，请求的时候是直接拼接到url的后面
     *      @Query("q") String q
     *      @QueryMap Map<String,String> map
     *
     * 2. 可替换块
     *      {...}表示的。可以替换，形成一个完整的路径
     *      @Path("...")
     *
     * 3. 表单提交：POST的方式
     *      @FormUrlEncoded 在方法上，表明他是一个表单提交
     *      @Field("username")String username
     *      @FieldMap Map<String,String> map 可以将多条构建到一个Map里面
     *
     * 4. 多部分
     *      @Multipart 在方法上，表明他是一个多部分提交
     *      @Part("..")
     *      @PartMap
     */

//    String url = "http://www.baidu.com/{user}/message/name";

    // GET请求：表明是一个GET请求，在后面可以直接加入url，指明路径
    @GET("https://api.github.com/search/repositories")
    Call<RepoResult> getData(@Query("q")String q, @Query("page")int page);


    // POST请求
    @POST("http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=register")
    Call<ResponseBody> register(@Body RequestBody body);


    // 表单请求:@FormUrlEncoded 表明他是一个表单提交
    @FormUrlEncoded
    @POST("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=register")
    Call<ResponseBody> getForm(@Field("username")String username, @Field("password")String password);


    // 多部分请求：@Multipart 表明他是一个多部分
    @Multipart
    @POST("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=update")
    Call<ResponseBody> getMult(@Part("user") RequestBody requestBody);


    // 多部分请求：在@Part后面不加name的时候，类型需要变化
    @Multipart
    @POST("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=update")
    Call<ResponseBody> getMult1(@Part MultipartBody.Part requestBody);


    //------------------------------------------------------------------------

    @GET("http://www.baidu.com")
    // 添加固定的请求头信息，如果需要动态修改，是在参数里面
    @Headers({"X-type:so","X-type:aa","Accept-type:en"})
    Call<ResponseBody> getA(@Header("Accept-type")String type, @HeaderMap Map<String,String> map);

    // url的可替换块的处理
    @GET("group/{id}/users")
    Call<ResponseBody> getB(@Path("id")String id);

}
