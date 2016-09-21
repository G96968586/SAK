package com.xhj.huijian.library;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.xhj.huijian.library.interfaces.IPresenter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gavin on 16/7/17 下午9:10.
 * huijian.xhj@alibaba-inc.com
 * 基类,封装业务无关的业务逻辑
 */
public abstract class BaseActivity extends FragmentActivity {
    private static final String TAG = BaseActivity.class.getCanonicalName();
    private Intent mIntent;
    private Bundle mBundle;
    protected ProgressDialog mProgressDialog;
    private Set<IPresenter> mAllPresenters = new HashSet<IPresenter>(1);

    /**
     * 显示一个view控件
     *
     * @param view
     */
    public void show(View view) {
        if (null != view) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏一个view控件
     *
     * @param view
     */
    public void hide(View view) {
        if (null != view) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Short Toast
     * @param toast
     */
    public void show(String toast) {
        showToast(toast, Toast.LENGTH_SHORT);
    }

    /**
     *
     * @param toast
     * @param duration
     */
    public void showToast(String toast, int duration) {
        Toast.makeText(getApplicationContext(), toast, duration).show();
    }

    /**
     * 强制退出app, 可以控制是否 show toast
     *
     * @param msg
     * @param time
     * @param showToast
     */
    public void forceExit(final String msg, final int time, final boolean showToast) {
        if (showToast) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Looper.prepare();
                    showToast(msg, Toast.LENGTH_LONG);
                    Looper.loop();
                }
            }).start();
        }

        // 暂停 time 秒
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 强制退出程序
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getApplicationContext().startActivity(intent);
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 强制退出app
     *
     * @param msg
     * @param time
     */
    public void forceExit(final String msg, final int time) {
        forceExit(msg, time, true);
    }

    /**
     * 采用默认主题的progress提示框
     *
     * @param title
     * @param message
     * @return
     */
    public ProgressDialog showProgress(String title, String message) {
        return showProgress(title, message, -1);
    }

    /**
     * 带主题样式参数的progress等待提示框
     *
     * @param title
     * @param message
     * @param theme
     * @return
     */
    public ProgressDialog showProgress(String title, String message, int theme) {
        if (mProgressDialog == null) {
            if (theme > 0)
                mProgressDialog = new ProgressDialog(this, theme);
            else
                mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCanceledOnTouchOutside(false);// 不能取消
            mProgressDialog.setIndeterminate(true);// 设置进度条是否不明确
        }

        if (mProgressDialog.isShowing())
            return null;

        if (!TextUtils.isEmpty(title))
            mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        return mProgressDialog;
    }

    /**
     * 取消提示框显示
     */
    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void disable(View view) {
        if (null != view) {
            view.setEnabled(false);
        }
    }

    public void enable(View view) {
        if (null != view) {
            view.setEnabled(true);
        }
    }

    /**
     * 需要子类来实现，获取子类的 IPresenter，一个 activity 有可能有多个 IPresenter
     * @return
     */
    protected abstract IPresenter[] getPresenters();

    /**
     * 调用各 presenter 的 init 方法
     */
    protected abstract void callPresenterInitMethod();

    /**
     * 初始化 Presenters
     */
    private void initPresenters(){

        IPresenter[] presenters = getPresenters();
        int len = presenters.length;
        if(presenters != null){
            for(int i = 0; i < len; i++){
                mAllPresenters.add(presenters[i]);
            }
        }
        callPresenterInitMethod();
    }

    /**
     * 获取 layout 的 id，具体由子类实现
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 从 intent 中解析数据，具体子类来实现
     * @param intent
     * @param bundle
     */
    protected void parseArgumentsFromIntent(Intent intent, Bundle bundle) {
    }

    /**
     * 提供 setContentView 方法之前处理一些业务逻辑
     */
    protected void beforeContentView() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // do something before setContentView
        beforeContentView();
        setContentView(getLayoutResId());
        // 拒绝本地服务漏洞
        try {
            mIntent = getIntent();
            mBundle = mIntent != null ? mIntent.getExtras() : null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        parseArgumentsFromIntent(mIntent, mBundle);

        initPresenters();
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (IPresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onStart();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (IPresenter presenter: mAllPresenters) {
            if (presenter != null) {
                presenter.onResume();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (IPresenter presenter: mAllPresenters) {
            if (presenter != null) {
                presenter.onPause();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (IPresenter presenter: mAllPresenters) {
            if (presenter != null) {
                presenter.onStop();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (IPresenter presenter: mAllPresenters) {
            if (presenter != null) {
                presenter.onDestroy();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
