package com.blcheung.hybridappdemo.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * SharedPreferences辅助类
 * 用来保存，登录，验证用户信息
 *
 * @Author BLCheung
 * @CreatDate 2019/11/5
 */
public class SharePrefsHelper {
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_PASSWORD = "password";
    private static final String KEY_AUTO_LOGIN = "auto_login";

    private Context mContext;
    private SharedPreferences sp;

    public SharePrefsHelper(Context mContext) {
        this.mContext = mContext;
        sp = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    public boolean saveUser(JSONObject user) {
        SharedPreferences.Editor editor = sp.edit();
        try {
            editor.putString(user.getString(KEY_USERNAME), user.getString(KEY_PASSWORD));
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return editor.commit();
    }

    /**
     * 提取用户
     *
     * @param userName
     * @return
     */
    public String loadUser(String userName) {
        return sp.getString(userName, "");
    }

    /**
     * 记录自动登录的用户信息
     *
     * @param userName
     */
    public void setAutoUser(String userName) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_AUTO_LOGIN, userName);
        editor.commit();
    }

    /**
     * 获取自动登录的用户信息
     */
    public String getAutoUser() {
        return sp.getString(KEY_AUTO_LOGIN, "");
    }

    /**
     * 清空自动登录保存的用户信息
     *
     * @return
     */
    public boolean clearAutoUser() {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(KEY_AUTO_LOGIN);
        return editor.commit();
    }
}
