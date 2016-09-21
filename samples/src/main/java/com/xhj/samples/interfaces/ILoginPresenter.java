package com.xhj.samples.interfaces;

import com.xhj.huijian.library.interfaces.IPresenter;

/**
 * Created by Gavin on 16/9/21 下午1:40.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public interface ILoginPresenter extends IPresenter<ILoginView> {
    void login(String name,String password);
}
