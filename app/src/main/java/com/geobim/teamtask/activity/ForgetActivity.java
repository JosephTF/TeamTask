package com.geobim.teamtask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

/**
 * 忘记密码
 * Created by Administrator on 2017/12/4.
 */

public class ForgetActivity extends BaseActivity implements OnClickListener {
    private ImageButton ib_back;
    private ImageView iv_country;
    private TextView tv_country;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget);
        StatusBarUtil.setTranslucent(ForgetActivity.this, 0);//状态栏半透明
        ib_back = findViewById(R.id.ib_forget_return);
        iv_country =findViewById(R.id.iv_forget_countrynum);
        tv_country=findViewById(R.id.tv_forget_countrynum);

        ib_back.setOnClickListener(this);
        iv_country.setOnClickListener(this);
        tv_country.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_forget_return:
                Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_forget_countrynum:
            case R.id.tv_forget_countrynum:
                Intent intent_country = new Intent(ForgetActivity.this, CountryActivity.class);
                startActivityForResult(intent_country, 10001);
                break;
        }
    }

    /**
     * 虚拟返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
