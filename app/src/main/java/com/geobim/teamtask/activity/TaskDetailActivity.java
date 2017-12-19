package com.geobim.teamtask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.ui.widget.PopCLevelSelector;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.ib_country_back)
    ImageButton ib_country_back;
    @Bind(R.id.tv_more)
    TextView tv_more;

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

    @OnClick({R.id.tv_guanlian,R.id.rl_finish,R.id.rl_comment,R.id.rledit_view,R.id.tv_suoshu,R.id.ib_country_back,R.id.tv_more})
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
            case R.id.ib_country_back:
                finish();
                break;
            case R.id.tv_more:
                List<String> LevelList = new ArrayList<>();
                LevelList.add("刷新");
                LevelList.add("关闭");
                LevelList.add("删除");
                LevelList.add("分享");
                PopCLevelSelector mLevelPopup = PopCLevelSelector.getInstance();
                mLevelPopup.pop(TaskDetailActivity.this, LevelList, tv_more);
                break;
        }
    }
}