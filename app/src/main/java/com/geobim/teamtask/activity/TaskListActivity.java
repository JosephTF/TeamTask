package com.geobim.teamtask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geobim.teamtask.R;
import com.geobim.teamtask.adapter.TaskListAdapter;
import com.geobim.teamtask.base.adapter.BaseListAdapter;
import com.geobim.teamtask.base.ui.BaseListActivity;
import com.geobim.teamtask.base.utils.ListSettings;
import com.geobim.teamtask.ui.PopupWindow.PopScreening;
import com.geobim.teamtask.ui.PopupWindow.PopSorting;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 任务列表
 */
public class TaskListActivity extends BaseListActivity<String> {

    @Bind(R.id.partDetails)
    ImageButton ll_partDetails;
    @Bind(R.id.tv_last_update)
    TextView tv_last_update;
    @Bind(R.id.tv_canyu)
    TextView tv_canyu;
    @Bind(R.id.ll_view11)
    LinearLayout ll_view11;
    @Bind(R.id.back)
    View back;
    @Bind(R.id.tv_saixuan)
    TextView tv_saixuan;
    @Bind(R.id.statusBarView)
    View statusBarView;
    @Bind(R.id.tv_right_title)
    TextView tv_right_title;
    @Bind(R.id.ib_country_back)
    ImageButton ib_country_back;

    private PopSorting popSorting;  //排序
    private PopScreening popScreening;  //筛选

    List<String> mData = new ArrayList<>();

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
    protected void OnViewCreated() {
        ButterKnife.bind(this);
        appbar.setVisibility(View.GONE);
        //状态栏透明
        StatusBarTransparent(this, statusBarView);
        for (int i = 0; i < 5; i++) {
            mData.add("任务" + i);
        }
        onDataLoaded(mData);

        //排序
        popSorting = new PopSorting(this,
                ll_view11, ll_view11, back, tv_canyu);
        //筛选
        popScreening = new PopScreening(this,
                ll_view11, ll_view11, back, tv_saixuan);
    }

    @Override
    public void requestData(boolean needRefresh) {
        for (int i = 0; i < 7; i++) {
            mData.add("任务" + i);
        }
        onDataLoaded(mData);
    }

    @Override
    protected BaseListAdapter<String> getListAdapter() {
        return new TaskListAdapter(this);
    }

    @Override
    protected ListSettings getBaseSettings() {
        ListSettings settings = new ListSettings();
        settings.setCustomLayoutId(R.layout.activity_tack_list);
        settings.setCanLoadMore(false);
        settings.setCanRefresh(true);
        return settings;
    }

    @OnClick({R.id.layout_right,R.id.partDetails,R.id.tv_last_update,R.id.tv_canyu,R.id.tv_saixuan,R.id.tv_right_title,R.id.ib_country_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right_title:
                startActivity(new Intent(TaskListActivity.this, TaskSearchActivity.class));
                break;
            case R.id.ib_country_back:
                finish();
                break;
            case R.id.partDetails:
                 startActivity(new Intent(TaskListActivity.this, AddTaskActivity.class));
                break;
            case R.id.tv_saixuan:
                popScreening.show();
                break;
            case R.id.tv_canyu:
                popSorting.show(1);
                popSorting.setOnBackClick(new PopSorting.OnBackClicks() {
                    @Override
                    public void onClick(View v) {
                        popSorting.dismissPop();
                    }
                });

                popSorting.setOnItemClick(new PopSorting.OnClicks() {
                    @Override
                    public void onClick(View v, int item) {
                        popSorting.dismissPop();
//                        showLoading();
                        onDataLoaded(null);
                    }
                });
                break;
            case R.id.tv_last_update:
                Toast.makeText(TaskListActivity.this,"排序--最后更新&创建时间&结束日期",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
