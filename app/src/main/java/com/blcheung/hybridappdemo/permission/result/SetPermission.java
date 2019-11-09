package com.blcheung.hybridappdemo.permission.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

/**
 * @Author BLCheung
 * @CreatDate 2019/11/5
 */
public class SetPermission {
    /**
     * 打开APP详情页面，引导用户去设置权限
     *
     * @param activity        页面对象
     * @param permissionNames 权限名称（如是多个，使用\n分割）
     */
    public static void openAppDetails(final Activity activity, String permissionNames) {
        StringBuilder sb = new StringBuilder();
        sb.append(PermissionUtil.PermissionTip1);
        sb.append(permissionNames);
        sb.append(PermissionUtil.PermissionTip2);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(sb.toString());
        builder.setPositiveButton(PermissionUtil.PermissionDialogPositiveButton, (dialog, which) -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            activity.startActivity(intent);
        });
        builder.setNegativeButton(PermissionUtil.PermissionDialogNegativeButton, (dialog, which) ->
                activity.finish());
        builder.setCancelable(false);
        builder.show();
    }

    public static void openAppDetails(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("授权失败！请给APP授权，否则功能将无法使用！");
        builder.setPositiveButton(PermissionUtil.PermissionDialogPositiveButton, (dialog, which) -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            activity.startActivity(intent);
        });
        builder.setNegativeButton(PermissionUtil.PermissionDialogNegativeButton, (dialog, which) ->
                activity.finish());
        builder.setCancelable(false);
        builder.show();
    }
}
