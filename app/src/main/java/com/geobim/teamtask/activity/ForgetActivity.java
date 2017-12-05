package com.geobim.teamtask.activity;

import android.os.Bundle;

import com.geobim.teamtask.R;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

/**
 * Created by Administrator on 2017/12/4.
 */

public class ForgetActivity extends BaseActivity {
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget);
        StatusBarUtil.setTranslucent(ForgetActivity.this, 0);//状态栏半透明
    }

    @Override
    protected void loadData() {

    }
}
