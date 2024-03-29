package com.hsh.dongzi.jinshang.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.widget.Toast;

import com.hsh.dongzi.jinshang.MyApplication;

/**
 * Created by lijiacheng on 2017/10/7.
 */

public final class ToastUtil {

    private ToastUtil() {

    }

    private static Toast mToast = null;

    /**
     * 获取资源
     */
    private static Resources getResources() {
        return MyApplication.getInstance().getResources();
    }

    /**
     * 获取文字
     */
    private static String getString(int resId) {
        return getResources().getString(resId);
    }

    private static long getMainThreadId() {
        return MyApplication.getMainThreadId();
    }

    /**
     * 获取主线程的handler
     */
    private static Handler getHandler() {
        return MyApplication.getMainThreadHandler();
    }

    /**
     * 在主线程执行runnable
     */
    private static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 判断当前的线程是不是在主线程
     *
     * @return
     */
    private static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final int resId) {
        showToastSafe(getString(resId));
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final String str) {
        if (isRunInMainThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    private static void showToast(String str) {
        Context context = MyApplication.getInstance();
        if (context != null) {
            showToast(context, str);
        }
    }

    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
