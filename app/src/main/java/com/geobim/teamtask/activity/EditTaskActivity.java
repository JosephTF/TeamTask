package com.geobim.teamtask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑任务
 */
public class EditTaskActivity extends BaseActivity {

    private int YEAR, MONTH, DAY;

    @Bind(R.id.rl_view1)
    RelativeLayout rl_view1;
    @Bind(R.id.rl_view2)
    RelativeLayout rl_view2;
    @Bind(R.id.rl_view3)
    RelativeLayout rl_view3;
    @Bind(R.id.rl_son_task)
    RelativeLayout rl_son_task;
    @Bind(R.id.ib_country_back)
    ImageButton ib_country_back;
    @Bind(R.id.tv_right_title)
    TextView tv_right_title;

    @Override
    protected void initVariables() {}

    @Override
    protected void initViews(Bundle savedInstanceState) {}

    @Override
    protected void loadData() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this, 0);//状态栏半透明;

        initDate();
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        YEAR = calendar.get(Calendar.YEAR);
        MONTH = calendar.get(Calendar.MONTH);
        DAY = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @OnClick({R.id.rl_view1, R.id.rl_view2, R.id.rl_view3,R.id.rl_son_task,R.id.ib_country_back, R.id.tv_right_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_view1:
                break;
            case R.id.rl_view2:
                break;
            case R.id.rl_view3:
                break;
            case R.id.rl_son_task:
                Intent intent = new Intent(this, TaskListActivity.class);
                intent.putExtra("pagePars", "1");
                startActivity(intent);
                break;
            case R.id.ib_country_back:
                finish();
                break;
            case R.id.tv_right_title:
                finish();
                break;
        }
    }
}
