package com.xhj.samples.presenters;

import com.xhj.samples.activity.RegisterActivity;
import com.xhj.samples.entity.UserInfo;
import com.xhj.samples.interfaces.IRegisterPresenter;
import com.xhj.samples.interfaces.IRegisterView;
import com.xhj.samples.listener.RegisterListener;
import com.xhj.samples.model.RegisterManager;

/**
 * Created by Gavin on 16/9/21 下午8:25.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public class RegisterPresenter implements IRegisterPresenter {
    private IRegisterView mRegisterView;
    private RegisterActivity mActivity;
    private RegisterManager mManager = RegisterManager.getInstance();

    @Override
    public void register(String name, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            mActivity.show("前后两次密码不一致");
            return;
        }
        mRegisterView.showLoadingView();
        mManager.regist(name, password, new RegisterListener() {
            @Override
            public void onSuccessRegister(UserInfo userInfo) {
                mRegisterView.onShowSuccessRegisterView(userInfo);
                mRegisterView.hideLoadingView();
            }

            @Override
            public void onFailedRegister(int errorCode) {
                mRegisterView.onShowFailedRegisterView(errorCode);
                mRegisterView.hideLoadingView();
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
    public void init(IRegisterView view) {
        mRegisterView = view;
        mRegisterView.initView();
        mActivity = (RegisterActivity) mRegisterView;
    }
}
