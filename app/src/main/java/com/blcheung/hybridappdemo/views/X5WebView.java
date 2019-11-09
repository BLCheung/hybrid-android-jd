package com.blcheung.hybridappdemo.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.app.AlertDialog;

import com.blcheung.hybridappdemo.jsInterface.MyJaveScriptInterface;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Map;

/**
 * @Author BLCheung
 * @CreatDate 2019/10/7
 */
public class X5WebView extends WebView {
    private Context mContext;
    private OnWebViewListener webViewListener;

    public void setOnWebViewListener(OnWebViewListener webViewListener) {
        this.webViewListener = webViewListener;
    }

    public X5WebView(Context context, boolean b) {
        super(context, b);
        init(context);
    }

    public X5WebView(Context context) {
        super(context);
        init(context);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
        init(context);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;

        // 初始化网页相关设置
        initWebViewSettings();
        initWebViewClient();
        initWebChromeClient();

        /**
         * 构建JSBridge对象，为web端提供Android端方法
         * 这里提供的“Android”字符串会被挂载到web端的window对象下面
         * 网页js下调用方法为window.android.方法名(参数)
         */
        addJavascriptInterface(new MyJaveScriptInterface(mContext), "android");
    }

    private void initWebViewSettings() {
        WebSettings webSettings = getSettings();
        // 设置允许网页加载js
        webSettings.setJavaScriptEnabled(true);
        // 不允许缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        // 设置缓存策略
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private void initWebViewClient() {
        // 设置网页在app内部打开，而不是用外部浏览器
        setWebViewClient(new WebViewClient() {
        });
    }

    private void initWebChromeClient() {
        setWebChromeClient(new WebChromeClient() {
            /**
             * 监听网页事件，网页加载事件
             * @param webView
             * @param i
             */
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (webViewListener != null) {
                    // 回调出去
                    webViewListener.onProgressChanged(webView, i);
                }
            }

            /**
             * 监听网页事件，使用原生端弹窗代替web端弹窗alert
             */
            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(s)
                        .setMessage(s1)
                        .setNegativeButton("确定", null)
                        .create()
                        .show();
                jsResult.confirm();
                return true;
            }
        });
    }

    public interface OnWebViewListener {
        /**
         * 网页进度加载事件
         */
        void onProgressChanged(WebView webView, int progress);
    }
}
