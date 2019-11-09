package com.blcheung.hybridappdemo.permission.result;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author BLCheung
 * @CreatDate 2019/11/6
 */
public class RequestPermissionResult implements IRequestPermissionResult {
    private static RequestPermissionResult requestPermissionResult;

    public static RequestPermissionResult getInstance() {
        if (requestPermissionResult == null) {
            requestPermissionResult = new RequestPermissionResult();
        }
        return requestPermissionResult;
    }


    @Override
    public boolean requestPermissionResult(Activity activity, String[] permissions, int[] grantResults) {
        List<String> deniedPermissionList = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                deniedPermissionList.add(permissions[i]);
            }
        }

        // 未授权
        if (deniedPermissionList.size() > 0) {
            // 引导用户去授权
            String permissionNames = PermissionUtil.getInstance().getPermissionNames(deniedPermissionList);
            SetPermission.openAppDetails(activity, permissionNames);
        } else {
            // 已全部授权
            Log.i("Permission", "requestPermissionResult: 执行了true");
            return true;
        }

        return false;
    }
}
