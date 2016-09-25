package com.xhj.samples.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xhj.huijian.library.interfaces.IPresenter;
import com.xhj.samples.R;
import com.xhj.samples.SampleBaseActivity;
import com.xhj.samples.entity.UserInfo;
import com.xhj.samples.interfaces.ILoginView;
import com.xhj.samples.presenters.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends SampleBaseActivity implements ILoginView{
    private static final String TAG = "LoginActivity";
    private LoginPresenter mLoginPresenter = new LoginPresenter();
    @BindView(R.id.login_et_username)
    EditText mName;
    @BindView(R.id.login_et_password)
    EditText mPassword;
    @BindView(R.id.login_button)
    Button mLoginButton;
    @BindView(R.id.cv)
    CardView mCardView;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;
    /**
     * 使用 ButterKnife 来控制 view 的 enabled 和 disable
     */
    private static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override public void apply(View view, int index) {
            view.setEnabled(false);
        }
    };
    private static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };

    /**
     * 两个输入框都非空时登录按钮才可点击
     */
    private TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mName.getText().length() > 0 && mPassword.getText().length() > 0) {
                ButterKnife.apply(mLoginButton, ENABLED, true);
//                enable(mLoginButton);
            } else {
//                disable(mLoginButton);
                ButterKnife.apply(mLoginButton, DISABLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected IPresenter[] getPresenters() {
        return new IPresenter[]{mLoginPresenter};
    }

    @Override
    protected void callPresenterInitMethod() {
        mLoginPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onShowSuccessLoginView(UserInfo userInfo) {
        Explode explode = new Explode();
        explode.setDuration(500);

        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(LoginActivity.this, ResultActivity.class);
        intent.putExtra("userId", userInfo.userId);
        intent.putExtra("userName", userInfo.userName);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    @Override
    public void onShowFailedLoginView(int errorCode) {
        show("登录出错了");
    }

    @Override
    public void showLoadingView() {
        showProgress("", "登录中");
    }

    @Override
    public void hideLoadingView() {
        hideProgress();
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        ButterKnife.bind(this);
        ButterKnife.apply(mLoginButton, DISABLE);
//        disable(mLoginButton);
        mName.addTextChangedListener(mWatcher);
        mPassword.addTextChangedListener(mWatcher);
    }

    @OnClick(R.id.login_button)
    public void login() {
        mLoginPresenter.login(mName.getText().toString(), mPassword.getText().toString());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.fab)
    public void floatingAction() {
        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, mFloatingActionButton, mFloatingActionButton.getTransitionName());
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
}
