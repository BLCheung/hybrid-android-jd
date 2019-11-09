package com.blcheung.hybridappdemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.blcheung.hybridappdemo.R;

/**
 * 状态栏工具类
 *
 * @Author BLCheung
 * @CreatDate 2019/10/16
 */
public class StatusBarUtil {
    /**
     * 设置透明状态栏
     *
     * @param activity 当前Activity
     */
    public static void setStatusBar(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setStatusBarColor(activity, activity.getResources().getColor(R.color.stateBarColor));
    }

    /**
     * 获取状态栏高度
     *
     * @param context 当前上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setNavigationBarColor(color);
            activity.getWindow().setStatusBarColor(color);
        }
    }
}
