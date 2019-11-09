package com.blcheung.hybridappdemo.permission;

import android.app.Activity;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import java.util.List;

/**
 * @Author BLCheung
 * @CreatDate 2019/11/5
 */
public class RequestPermission implements IRequestPermission {
    private static RequestPermission requestPermission;

    public static RequestPermission getInstance() {
        if (requestPermission == null) {
            requestPermission = new RequestPermission();
        }
        return requestPermission;
    }

    @Override
    public boolean requestPermission(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
//        return requestNeedPermission(activity, permissions, requestCode);
        return requestAllPermission(activity, permissions, requestCode);
    }

    private boolean requestNeedPermission(Activity activity, String[] permissions, int resultCode) {
        List<String> list = CheckPermission.checkPermissionDenied(activity, permissions);
        if (list.size() == 0) {
            return true;
        }

        // 请求权限
        String[] deniedPermissions = list.toArray(new String[0]);
        ActivityCompat.requestPermissions(activity, deniedPermissions, resultCode);
        return false;
    }

    private boolean requestAllPermission(Activity activity, String[] permissions, int resultCode) {
        // 判断是否已赋予了全部权限
        boolean isAllGranted = CheckPermission.checkPermissionAllGranted(activity, permissions);
        if (isAllGranted) {
            return true;
        }
        ActivityCompat.requestPermissions(activity, permissions, resultCode);
        return false;
    }
}
