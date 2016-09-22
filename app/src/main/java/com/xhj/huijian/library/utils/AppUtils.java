package com.xhj.huijian.library.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

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
     * @return 返回当前进程名称
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

    /**
     * 往设备桌面添加应用快捷方式,需要权限
     * {@code com.android.launcher.permission.INSTALL_SHORTCUT}
     * @param context 应用上下文
     * @param shortCutName 快捷方式名称
     * @param resourceId 快捷方式 icon 资源 ID
     */
    public static void addShortCut(Context context, String shortCutName, int resourceId) {
        Intent shortCutIntent = new Intent(context.getApplicationContext(), context.getClass());
        shortCutIntent.setAction(Intent.ACTION_MAIN);
        shortCutIntent.addCategory("android.intent.category.LAUNCHER");
        Intent addIntent = new Intent();
        addIntent.putExtra("duplicate", false);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortCutName);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context.getApplicationContext(),
                resourceId));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        context.getApplicationContext().sendBroadcast(addIntent);
    }
}
