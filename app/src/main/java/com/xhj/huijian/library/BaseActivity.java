package com.xhj.huijian.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Gavin on 16/7/17 下午9:10.
 * huijian.xhj@alibaba-inc.com
 */
public abstract class BaseActivity extends FragmentActivity {
    private static final String TAG = BaseActivity.class.getCanonicalName();
    private Intent mIntent;
    private Bundle mBundle;

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
     * 提供 setContentView 方法之前处理一些业务
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

        }
        parseArgumentsFromIntent(mIntent, mBundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

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
     * 强制退出app
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
}
