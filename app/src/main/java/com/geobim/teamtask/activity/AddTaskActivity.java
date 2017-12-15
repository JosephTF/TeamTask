package com.geobim.teamtask.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.geobim.teamtask.R;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加任务
 */
public class AddTaskActivity extends BaseActivity {

    @Bind(R.id.rl_list)
    RelativeLayout rl_list;
    @Bind(R.id.ll_view_list)
    LinearLayout ll_view_list;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_add);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this, 0);//状态栏半透明
    }

    @Override
    protected void loadData() {
    }

    @OnClick({R.id.rl_list,R.id.rl_date,R.id.rl_person_view,R.id.rl_fujina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_list:
                ll_view_list.addView(LayoutInflater.from(this).inflate(R.layout.task_add_item, null));
                break;
            case R.id.rl_date:
                ll_view_list.addView(LayoutInflater.from(this).inflate(R.layout.task_add_item, null));
                break;
            case R.id.rl_person_view:
                ll_view_list.addView(LayoutInflater.from(this).inflate(R.layout.task_add_item, null));
                break;
            case R.id.rl_fujina:
                ll_view_list.addView(LayoutInflater.from(this).inflate(R.layout.task_add_item, null));
                break;
        }
    }
}
