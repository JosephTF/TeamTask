package com.geobim.teamtask.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.base.ui.BaseLayoutActivity;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 任务完成
 */
public class FinishTaskActivity extends BaseLayoutActivity {

    @Bind(R.id.ib_country_back)
    ImageButton ib_country_back;
    @Bind(R.id.tv_right_title)
    TextView tv_right_title;

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this, 0);//状态栏半透明;
    }

    @OnClick({R.id.ib_country_back, R.id.tv_right_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_country_back:
                finish();
                break;
            case R.id.tv_right_title:
                finish();
                break;
        }
    }
}
