package com.xhj.samples.interfaces;

import com.xhj.huijian.library.interfaces.IView;
import com.xhj.samples.entity.UserInfo;

/**
 * Created by Gavin on 16/9/21 下午1:42.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public interface ILoginView extends IView {
    void onShowSuccessLoginView(UserInfo userInfo);
    void onShowFailedLoginView(int errorCode);
    void showLoginView();
    void hideLoginView();
}
