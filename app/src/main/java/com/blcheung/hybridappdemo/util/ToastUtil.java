package com.blcheung.hybridappdemo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @Author BLCheung
 * @CreatDate 2019/11/1
 */
public class ToastUtil {
    private static Toast toast;

    /**
     * 单例吐司
     *
     * @param context 上下文
     * @param content 内容
     */
    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
