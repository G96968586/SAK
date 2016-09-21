package com.xhj.samples.interfaces;

import com.xhj.huijian.library.interfaces.IView;
import com.xhj.samples.entity.UserInfo;

/**
 * Created by Gavin on 16/9/21 下午8:15.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public interface IRegisterView extends IView {
    void onShowSuccessRegisterView(UserInfo userInfo);
    void onShowFailedRegisterView(int errorCode);
    void showLoadingView();
    void hideLoadingView();
    void showEnterAnimation();
    void animateRevealShow();
    void animateRevealClose();
}
