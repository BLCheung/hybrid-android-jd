package com.blcheung.hybridappdemo.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blcheung.hybridappdemo.R;
import com.blcheung.hybridappdemo.base.BaseActivity;

public class PayResultActivity extends BaseActivity {
    public static final String KEY_GOODS_NAME = "name";
    private ImageView ivResult;
    private TextView tvName, tvResult;
    private String goodsName;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pay_result;
    }

    /**
     * 支付界面入口方法
     *
     * @param context 上下文
     */
    public static void show(Context context, String goodsName) {
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra(KEY_GOODS_NAME, goodsName);
        context.startActivity(intent);
    }


    @Override
    protected void initViews() {
        super.initViews();
        ivResult = findViewById(R.id.iv_result);
        tvName = findViewById(R.id.tv_name);
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    protected void initData() {
        super.initData();
        goodsName = getIntent().getStringExtra(KEY_GOODS_NAME);
        if (TextUtils.isEmpty(goodsName)) {
            tvName.setText("未获取到商品");
            tvResult.setText("支付失败");
            ivResult.setImageResource(R.mipmap.error);
        } else {
            tvName.setText(goodsName);
            tvResult.setText("支付成功");
            ivResult.setImageResource(R.mipmap.success);
        }
    }

    /**
     * 返回点击事件
     *
     * @param view
     */
    public void onBackClick(View view) {
        onBackPressed();
    }
}
