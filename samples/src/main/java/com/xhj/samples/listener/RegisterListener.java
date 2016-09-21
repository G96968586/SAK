package com.xhj.samples.listener;

import com.xhj.samples.entity.UserInfo;

/**
 * Created by Gavin on 16/9/21 下午8:31.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public interface RegisterListener {
    void onSuccessRegister(UserInfo userInfo);
    void onFailedRegister(int errorCode);
}
