package com.xhj.huijian.library.utils;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Gavin on 16/9/25 下午10:24.
 * huijian.xhj@alibaba-inc.com
 * Project Name: Library.
 * 文件工具类
 */

public class FileUtils {
    /**
     * 从 Assets 里读取指定文件内容并返回字符串对象
     * @param context 上下文
     * @param file 文件名
     * @return 以字符串形式返回文件内容
     */
    public static String getDataFromAssets(Context context, String file) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(file);
            if (inputStream == null){
                return null;
            }
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
