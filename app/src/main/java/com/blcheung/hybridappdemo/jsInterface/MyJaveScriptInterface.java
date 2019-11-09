package com.blcheung.hybridappdemo.jsInterface;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import androidx.appcompat.app.AlertDialog;

import com.blcheung.hybridappdemo.helper.SharePrefsHelper;
import com.blcheung.hybridappdemo.pay.PayResultActivity;
import com.blcheung.hybridappdemo.util.StatusBarUtil;
import com.blcheung.hybridappdemo.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Android提供给JavaScript接口
 * 为JavaScript提供Android的方法
 *
 * @Author BLCheung
 * @CreatDate 2019/10/7
 */
public class MyJaveScriptInterface {
    private Context mContext;
    private SharePrefsHelper mSpHelper;

    public MyJaveScriptInterface(Context mContext) {
        this.mContext = mContext;
        this.mSpHelper = new SharePrefsHelper(mContext);
    }

    /**
     * 注册
     *
     * @param user 用户JSON对象
     * @return -1：未知错误 0：已存在该用户 1：注册成功
     */
    @JavascriptInterface
    public String register(String user) {
        String result = "-1";
        try {
            JSONObject obj = new JSONObject(user);
            // 判断有没有该用户
            String password = mSpHelper.loadUser(obj.getString(SharePrefsHelper.KEY_USERNAME));
            if (TextUtils.isEmpty(password)) {
                // 没有被注册过
                mSpHelper.saveUser(obj);
                result = "1";
            } else {
                // 已存在该用户
                result = "0";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 登录
     *
     * @param user
     * @return -1：未知错误 0：用户不存在 1：登录成功 2：密码错误
     */
    @JavascriptInterface
    public String login(String user) {
        String result = "-1";
        try {
            JSONObject obj = new JSONObject(user);
            String password = mSpHelper.loadUser(obj.getString(SharePrefsHelper.KEY_USERNAME));
            // 判断有没有该用户对应的键名(值为密码)，如果没有则为空值
            if (TextUtils.isEmpty(password)) {
                // 没有该用户
                result = "0";
            } else {
                if (password.equals(obj.getString(SharePrefsHelper.KEY_PASSWORD))) {
                    // 匹配
                    mSpHelper.setAutoUser(obj.getString(SharePrefsHelper.KEY_USERNAME));
                    result = "1";
                } else {
                    // 不匹配
                    result = "2";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 注销
     *
     * @return true注销成功，反之失败
     */
    @JavascriptInterface
    public boolean logout() {
        return mSpHelper.clearAutoUser();
    }

    /**
     * 跳转支付界面
     */
    @JavascriptInterface
    public void pay(String goods) {
        String name = "";
        try {
            JSONObject obj = new JSONObject(goods);
            name = obj.getString(PayResultActivity.KEY_GOODS_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PayResultActivity.show(mContext, name);
    }

    /**
     * 提供给网页js调用的方法
     * 该方法为原生弹窗展示从js传递过来的字符串
     *
     * @param str js传递过来的字符串
     */
    @JavascriptInterface
    public void androidAlert(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(str)
                .setNegativeButton("确定", null)
                .create()
                .show();
    }

    @JavascriptInterface
    public int getStatusBarHeight() {
        return StatusBarUtil.getStatusBarHeight(mContext);
    }

    /**
     * 提供给web端Toast方法
     *
     * @param content
     */
    @JavascriptInterface
    public void showToast(String content) {
        ToastUtil.showToast(mContext, content);
    }

    /**
     * 为js返回一个返回值
     *
     * @return
     */
    @JavascriptInterface
    public String androidTest() {
        return "来自Android方法的回调！";
    }
}
