package com.geobim.teamtask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;

import com.geobim.teamtask.R;

/**
 * 安全管理Activity
 * Created by Administrator on 2017/12/20.
 */

public class SecurityActivity extends BaseActivity implements OnClickListener {
    private ImageView iv_back;
    private RelativeLayout rl_resetpwd, rl_loginhistory;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security);
        iv_back = (ImageView) findViewById(R.id.ib_security_back);
        rl_resetpwd = (RelativeLayout) findViewById(R.id.rl_security_resetpsw);
        rl_loginhistory = (RelativeLayout) findViewById(R.id.rl_security_loginhistory);
        iv_back.setOnClickListener(this);
        rl_resetpwd.setOnClickListener(this);
        rl_loginhistory.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_about_back:
                finish();
                break;
            case R.id.rl_security_resetpsw:
                Intent intent2resetpwd = new Intent(SecurityActivity.this, ResetPasswordActivity.class);
                startActivity(intent2resetpwd);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.rl_security_loginhistory:
                Intent intent2loginhistory = new Intent(SecurityActivity.this, LoginHistoryActivity.class);
                startActivity(intent2loginhistory);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
            finish();
        }
        return false;
    }
}
