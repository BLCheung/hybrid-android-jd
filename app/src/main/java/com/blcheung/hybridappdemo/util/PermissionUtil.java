package com.blcheung.hybridappdemo.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

/**
 * @Author BLCheung
 * @CreatDate 2019/11/5
 */
public class PermissionUtil {
    /**
     * 跳转到app详情页
     *
     * @param context 当前上下文
     */
    public static void ask4Permission(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("You need to access all permission")
                .setNegativeButton("CANCEL", null)
                .setPositiveButton("SETTING", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                        context.startActivity(intent);
                    }
                })
                .create()
                .show();
    }

    /**
     * 检查当前上下文所需权限是否通过授权
     *
     * @param context 上下文
     * @param perms   所需权限
     * @return
     */
    public static boolean hasPermissions(Context context, String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;

        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }
}
