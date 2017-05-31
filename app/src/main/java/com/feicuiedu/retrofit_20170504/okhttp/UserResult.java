package com.feicuiedu.retrofit_20170504.okhttp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gqq on 2017/5/4.
 */

public class UserResult {

    /**
     * errcode : 1
     * errmsg : 注册成功！
     * tokenid : 171
     *
     * @SerializedName("errcode")
     * 目的是为了将Json字符串的key和实体类的属性名关联
     * err_code_aa
     * errCodeAa
     */

    @SerializedName("errcode")
    private int code;
    @SerializedName("errmsg")
    private String msg;
    private int tokenid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTokenid() {
        return tokenid;
    }

    public void setTokenid(int tokenid) {
        this.tokenid = tokenid;
    }
}
