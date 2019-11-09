package com.blcheung.hybridappdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.blcheung.hybridappdemo.base.BaseActivity;
import com.blcheung.hybridappdemo.constant.Common;
import com.blcheung.hybridappdemo.helper.SharePrefsHelper;
import com.blcheung.hybridappdemo.permission.IRequestPermission;
import com.blcheung.hybridappdemo.permission.RequestPermission;
import com.blcheung.hybridappdemo.permission.result.PermissionUtil;
import com.blcheung.hybridappdemo.permission.result.SetPermission;
import com.blcheung.hybridappdemo.util.ToastUtil;
import com.blcheung.hybridappdemo.views.X5WebView;

/**
 * @ClassName MainActivity
 * @Description
 * @Author BLCheung
 * @CreateDate 2019/10/7 0:55
 */
public class MainActivity extends BaseActivity {
    private X5WebView mWebView;
    private SharePrefsHelper mPrefsHelper;
    private IRequestPermission mRequestPermission = RequestPermission.getInstance();


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mPrefsHelper = new SharePrefsHelper(this);
        mWebView = findViewById(R.id.main_webview);
    }

    @Override
    protected void initData() {
        super.initData();
        if (requestPermission()) {
            setWebView();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (requestPermission()) {
            setWebView();
            return;
        } else {
            ToastUtil.showToast(this, "请给APP授权，否则功能将无法使用！");
            requestPermission();
        }
    }

    private void setWebView() {
        setWebView(mWebView);
        if (mWebView.getUrl() != null) {
            return;
        } else {
            mWebView.loadUrl(Common.WEB_URL);
            mWebView.setOnWebViewListener((webView, progress) -> {
                if (progress == 100) {
                    // 自动登录
                    isAutoUser();
                }
            });
        }
    }

    /**
     * 是否为自动登录
     * 判断当前设备是否保存了用户的信息
     * 如果存在则传递给web端，否则直接return，不做任何操作
     */
    private void isAutoUser() {
        String userName = mPrefsHelper.getAutoUser();
        if (TextUtils.isEmpty(userName)) {
            return;
        }

        // 调用web端方法，必须是绑定到window上面的方法
        mWebView.evaluateJavascript("javascript:nativeSetUserName('" + userName + "')", s -> {
            Log.i("JS", "isAutoUser: " + s);
        });
    }

    /**
     * 请求权限
     */
    private boolean requestPermission() {
        String[] permissions = new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE
        };

        return mRequestPermission.requestPermission(this, permissions, PermissionUtil.ResultCode1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.ResultCode1) {
            if (grantResults.length == 0) {
                ToastUtil.showToast(this, "授权失败！请给APP授权，否则功能将无法使用！");
                return;
            }

            for (int x : grantResults) {
                // 用户拒绝了某项权限
                if (x == PackageManager.PERMISSION_DENIED) {
                    ToastUtil.showToast(this, "授权失败！请给APP授权，否则功能将无法使用！");
                    // 打开App详情页
                    SetPermission.openAppDetails(this);
                    return;
                }
            }
            ToastUtil.showToast(this, "所有权限均授权成功！");
            setWebView();
        }
    }

    /**
     * 按钮点击事件
     *
     * @param view
     */
    public void onJSFunctionClick(View view) {
        // 调用web端JS函数："javascript:方法(参数)"
        mWebView.evaluateJavascript("javascript:onJSAlert()", s -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(s)
                    .setNegativeButton("确定", null)
                    .create()
                    .show();
        });
    }
}




