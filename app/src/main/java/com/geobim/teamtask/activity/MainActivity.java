package com.geobim.teamtask.activity;

import android.os.Bundle;

import com.geobim.teamtask.R;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 主界面
 * Created by Administrator on 2017/11/27.
 */

public class MainActivity extends BaseActivity implements OnClickListener {
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucent(MainActivity.this, 0);//状态栏半透明

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }
}
