package com.blcheung.hybridappdemo.permission;

import android.app.Activity;

/**
 * @Author BLCheung
 * @CreatDate 2019/11/5
 */
public interface IRequestPermission {
    /**
     * 请求权限
     *
     * @param activity    当前Activity
     * @param permissions 权限集合
     * @param requestCode 请求码
     * @return 如果所有权限通过，则true；反之false
     */
    boolean requestPermission(Activity activity, String[] permissions, int requestCode);
}
