package com.xhj.samples.activitiy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xhj.huijian.library.interfaces.IPresenter;
import com.xhj.samples.R;
import com.xhj.samples.SampleBaseActivity;
import com.xhj.samples.entity.UserInfo;
import com.xhj.samples.interfaces.ILoginView;
import com.xhj.samples.presenters.LoginPresenter;

public class LoginActivity extends SampleBaseActivity implements ILoginView{
    private static final String TAG = "LoginActivity";
    private LoginPresenter mLoginPresenter = new LoginPresenter();
    private EditText mName;
    private EditText mPassword;
    private Button mLoginButton;
    private CardView mCardView;
    private FloatingActionButton mFloatingActionButton;
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
                enable(mLoginButton);
            } else {
                disable(mLoginButton);
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
    public void showLoginView() {
        showProgress("", "登录中");
    }

    @Override
    public void hideLoginView() {
        hideProgress();
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        mName = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        mLoginButton = (Button) findViewById(R.id.bt_go);
        mCardView = (CardView) findViewById(R.id.cv);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        disable(mLoginButton);
        mName.addTextChangedListener(mWatcher);
        mPassword.addTextChangedListener(mWatcher);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.login(mName.getText().toString(), mPassword.getText().toString());
            }
        });
    }
}
