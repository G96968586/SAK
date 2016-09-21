package com.xhj.samples.listener;

import com.xhj.samples.entity.UserInfo;

/**
 * Created by Gavin on 16/9/21 下午5:01.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 * 模拟
 */

public interface LoginListener {
    void onSuccessLogin(UserInfo userInfo);
    void onFailedLogin(int errorCode);
}
