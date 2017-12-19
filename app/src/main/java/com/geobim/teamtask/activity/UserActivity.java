package com.geobim.teamtask.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.geobim.teamtask.R;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

/**
 * 用户详细信息Activity
 * Created by Administrator on 2017/12/18.
 */

public class UserActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        StatusBarUtil.setTranslucent(UserActivity.this, 0);//状态栏半透明

    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_user_back:
                finish();
                break;
        }
    }
}
