package com.xhj.samples.entity;

/**
 * Created by Gavin on 16/9/21 下午2:38.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public class UserInfo {
    public String userId;
    public String userName;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
