package com.brioal.todo.bean;

/**
 * 用户实体类
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/16.
 */

public class UserBean {
    private long mUserID;//用户唯一标识符
    private String mUserName = "";//用户名称
    private String mEmail = "";//用户邮箱
    private String mHeadUrl = "";//用户头像地址
    private String mPassWord = "";//用户密码

    public long getUserID() {
        return mUserID;
    }

    public UserBean setUserID(long userID) {
        mUserID = userID;
        return this;
    }

    public String getUserName() {
        return mUserName;
    }

    public UserBean setUserName(String userName) {
        mUserName = userName;
        return this;
    }

    public String getEmail() {
        return mEmail;
    }

    public UserBean setEmail(String email) {
        mEmail = email;
        return this;
    }

    public String getHeadUrl() {
        return mHeadUrl;
    }

    public UserBean setHeadUrl(String headUrl) {
        mHeadUrl = headUrl;
        return this;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public UserBean setPassWord(String passWord) {
        mPassWord = passWord;
        return this;
    }
}
