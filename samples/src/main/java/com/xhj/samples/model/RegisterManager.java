package com.xhj.samples.model;

import android.os.Handler;

import com.xhj.samples.entity.UserInfo;
import com.xhj.samples.listener.RegisterListener;

/**
 * Created by Gavin on 16/9/21 下午8:29.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */
public class RegisterManager {
    private static RegisterManager ourInstance = new RegisterManager();

    public static RegisterManager getInstance() {
        return ourInstance;
    }

    private RegisterManager() {
    }

    private static final Handler sHandler = new Handler();

    public void regist(final String name, final String password, final RegisterListener registerListener) {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UserInfo userInfo = new UserInfo();
                userInfo.userId = System.currentTimeMillis() + "";
                userInfo.userName = name;
                userInfo.setPassword(password);
                // 这里不模拟失败了,直接进入成功流程
                registerListener.onSuccessRegister(userInfo);
            }
        }, 2000);
    }
}
