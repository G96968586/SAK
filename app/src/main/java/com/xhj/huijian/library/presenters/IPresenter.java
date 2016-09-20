package com.xhj.huijian.library.presenters;

import com.xhj.huijian.library.views.IView;

/**
 * Created by Gavin on 16/9/20 下午11:42.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public interface IPresenter<V extends IView> {
    /**
     * 以下是 Presenter 的生命周期方法
     */
    void onStop();

    void onResume();

    void onDestroy();

    void onPause();

    void onStart();

    void init(V view);
}
