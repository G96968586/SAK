package com.xhj.samples.model;

import android.os.Handler;

import com.xhj.samples.entity.UserInfo;
import com.xhj.samples.listener.LoginListener;

/**
 * Created by Gavin on 16/9/21 下午4:53.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 * 模拟 Model
 */

public class LoginManager {
    private static final LoginManager INSTANCE = new LoginManager();
    private LoginManager() {}

    public static LoginManager getInstance() {
        return INSTANCE;
    }

    private static final Handler sHandler = new Handler();

    public void login(final String name, final String password, final LoginListener loginListener) {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("111111".equals(password)) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.userId = System.currentTimeMillis() + "";
                    userInfo.userName = name;
                    loginListener.onSuccessLogin(userInfo);
                } else {
                    loginListener.onFailedLogin(-1);
                }
            }
        },2000);
    }
}
