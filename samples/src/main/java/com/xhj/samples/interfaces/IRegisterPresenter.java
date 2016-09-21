package com.xhj.samples.interfaces;

import com.xhj.huijian.library.interfaces.IPresenter;

/**
 * Created by Gavin on 16/9/21 下午8:17.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 */

public interface IRegisterPresenter extends IPresenter<IRegisterView> {
    void register(String name, String password, String confirmPassword);
}
