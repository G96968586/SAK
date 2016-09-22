package com.xhj.huijian.library.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Gavin on 16/9/21 下午5:32.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 * App 通用工具类
 */

public class AppUtils {
    /**
     * 获取当前进程名称
     * @param context
     * @return
     */
    public static String getCurrentProcessName(Context context) {
        String name = null;
        final int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (pid == info.pid) {
                name = info.processName;
            }
        }
        return name;
    }
}
