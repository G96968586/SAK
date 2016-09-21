package com.xhj.samples.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.xhj.huijian.library.interfaces.IPresenter;
import com.xhj.samples.R;
import com.xhj.samples.SampleBaseActivity;
import com.xhj.samples.entity.UserInfo;
import com.xhj.samples.interfaces.IRegisterView;
import com.xhj.samples.presenters.RegisterPresenter;

/**
 * Created by Gavin on 16/9/21 下午8:15.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public class RegisterActivity extends SampleBaseActivity implements IRegisterView {
    private static final String TAG = "RegisterActivity";
    private FloatingActionButton mActionButton;
    private CardView mCardView;
    private Button mRegistButton;
    private EditText mName;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private RegisterPresenter mRegisterPresenter = new RegisterPresenter();
    private TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mName.getText().length() > 0 && mPassword.getText().length() > 0 && mConfirmPassword.getText().length() > 0) {
                enable(mRegistButton);
            } else {
                disable(mRegistButton);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected IPresenter[] getPresenters() {
        return new IPresenter[]{mRegisterPresenter};
    }

    @Override
    protected void callPresenterInitMethod() {
        mRegisterPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onShowSuccessRegisterView(UserInfo userInfo) {
        Explode explode = new Explode();
        explode.setDuration(500);

        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(RegisterActivity.this, ResultActivity.class);
        intent.putExtra("userId", userInfo.userId);
        intent.putExtra("userName", userInfo.userName);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    @Override
    public void onShowFailedRegisterView(int errorCode) {
        show("注册出错了");
    }

    @Override
    public void showLoadingView() {
        showProgress("", "注册中");
    }

    @Override
    public void hideLoadingView() {
        hideProgress();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                hide(mCardView);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView, mCardView.getWidth()/2,0, mActionButton.getWidth() / 2, mCardView.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mCardView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView,mCardView.getWidth()/2,0, mCardView.getHeight(), mActionButton.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                show(mCardView);
                super.onAnimationEnd(animation);
                mActionButton.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void initView() {
        mActionButton = (FloatingActionButton) findViewById(R.id.fab_close);
        mCardView = (CardView) findViewById(R.id.cv_add);
        mRegistButton = (Button) findViewById(R.id.bt_go);
        mName = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        mConfirmPassword = (EditText) findViewById(R.id.et_repeatpassword);
        
        disable(mRegistButton);
        mName.addTextChangedListener(mWatcher);
        mPassword.addTextChangedListener(mWatcher);
        mConfirmPassword.addTextChangedListener(mWatcher);
        mRegistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterPresenter.register(mName.getText().toString(), mPassword.getText().toString(), mConfirmPassword.getText().toString());
            }
        });
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
                Log.d(TAG, "i am here");
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showEnterAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
