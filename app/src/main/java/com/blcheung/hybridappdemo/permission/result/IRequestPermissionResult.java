package com.blcheung.hybridappdemo.permission.result;

import android.app.Activity;

/**
 * @Author BLCheung
 * @CreatDate 2019/11/5
 */
public interface IRequestPermissionResult {
    /**
     * 处理权限请求结果
     *
     * @param activity    当前Activity
     * @param permissions 请求的权限数组
     * @param grantResults 权限请求结果数组
     * @return 处理权限结果如果全部通过，返回true；否则，引导用户去授权页面
     */
    boolean requestPermissionResult(Activity activity, String[] permissions, int[] grantResults);
}
