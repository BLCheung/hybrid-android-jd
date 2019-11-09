package com.blcheung.hybridappdemo.base;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blcheung.hybridappdemo.constant.Common;
import com.blcheung.hybridappdemo.util.StatusBarUtil;
import com.blcheung.hybridappdemo.util.ToastUtil;
import com.blcheung.hybridappdemo.views.X5WebView;

/**
 * Activity基类
 *
 * @Author BLCheung
 * @CreatDate 2019/10/16
 */
public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = "LOG";
    private X5WebView mWebView;
    // 记录用户点击后退时间差
    private long endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        // 设置沉浸式状态栏
        StatusBarUtil.setStatusBar(this);
        initViews();
        initData();
    }

    /**
     * 获取当前布局Id
     *
     * @return 布局Id
     */
    protected abstract int getContentViewId();

    /**
     * 初始化控件
     */
    protected void initViews() {
        Log.d(TAG, "initViews: 初始化控件...");
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        Log.d(TAG, "initData: 初始化数据...");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否按下返回
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 网页是否可以后退
            if (mWebView.canGoBack() && !Common.WEB_URL.equals(mWebView.getUrl())) {
                // 执行后退
                mWebView.goBack();
                return true;
            }
            // 网页无法再后退，则“再按一次退出”
            if (System.currentTimeMillis() - endTime > 2000) {
                ToastUtil.showToast(this, "再按一次退出");
                endTime = System.currentTimeMillis();
            } else {
                // 结束
                finish();
            }
        }
        return true;
    }

    /**
     * 设置WebView
     *
     * @param webView
     */
    public void setWebView(X5WebView webView) {
        this.mWebView = webView;
    }
}
