package com.xhj.samples.presenters;

import com.xhj.huijian.library.interfaces.IView;
import com.xhj.samples.SampleBaseActivity;
import com.xhj.samples.activitiy.LoginActivity;
import com.xhj.samples.entity.UserInfo;
import com.xhj.samples.interfaces.ILoginPresenter;
import com.xhj.samples.interfaces.ILoginView;
import com.xhj.samples.listener.LoginListener;
import com.xhj.samples.model.LoginManager;

/**
 * Created by Gavin on 16/9/21 下午3:11.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public class LoginPresenter implements ILoginPresenter {
    private ILoginView mLoginView;
    private LoginManager mLoginManager = LoginManager.getInstance();
    private LoginActivity mActivity;

    @Override
    public void login(String name, String password) {
        if (password.length() < 6) {
            mActivity.show("密码不能少于6位");
            return;
        }
        mLoginView.showLoginView();
        mLoginManager.login(name, password, new LoginListener() {
            @Override
            public void onSuccessLogin(UserInfo userInfo) {
                mLoginView.onShowSuccessLoginView(userInfo);
                mLoginView.hideLoginView();
            }

            @Override
            public void onFailedLogin(int errorCode) {
                mLoginView.onShowFailedLoginView(errorCode);
                mLoginView.hideLoginView();
            }
        });
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void init(ILoginView view) {
        mLoginView = view;
        mLoginView.initView();
        mActivity = (LoginActivity) mLoginView;
    }
}
