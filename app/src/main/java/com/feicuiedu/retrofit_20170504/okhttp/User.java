package com.feicuiedu.retrofit_20170504.okhttp;

/**
 * Created by gqq on 2017/5/4.
 */

public class User {

    // 插件：GsonFormat Json字符串生成实体类的属性

    /**
     * Password : 654321
     * UserName : qjd
     */

    private String Password;
    private String UserName;

    public User(String password, String userName) {
        Password = password;
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }


}
