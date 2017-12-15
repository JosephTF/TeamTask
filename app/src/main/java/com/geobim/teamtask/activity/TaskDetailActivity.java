package com.geobim.teamtask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.geobim.teamtask.R;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 任务详情
 * Created by Administrator on 2017/12/5.
 */

public class TaskDetailActivity extends BaseActivity  {

    @Bind(R.id.rl_finish)
    RelativeLayout rl_finish;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_detal2);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(TaskDetailActivity.this, 0);//状态栏半透明
    }

    @Override
    protected void loadData() {
    }
    @OnClick({R.id.tv_guanlian,R.id.rl_finish,R.id.rl_comment,R.id.rledit_view,R.id.tv_suoshu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guanlian:
                startActivity(new Intent(this, TaskListActivity.class));
                break;
            case R.id.rl_finish:
                startActivity(new Intent(this, FinishTaskActivity.class));
                break;
            case R.id.rl_comment:
                startActivity(new Intent(this, CommentActivity.class));
                break;
            case R.id.rledit_view:
                startActivity(new Intent(this, EditTaskActivity.class));
                break;
            case R.id.tv_suoshu:
                startActivity(new Intent(this, TaskListActivity.class));
                break;
        }
    }
}