package com.geobim.teamtask.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.geobim.teamtask.R;
import com.geobim.teamtask.adapter.TaskNoticeListAdapter;
import com.geobim.teamtask.base.adapter.BaseListAdapter;
import com.geobim.teamtask.base.ui.BaseListActivity;
import com.geobim.teamtask.base.utils.ListSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 任务通知
 */
public class TaskNoticeActivity extends BaseListActivity<String> {

    @Bind(R.id.statusBarView)
    View statusBarView;
    @Bind(R.id.ib_country_back)
    ImageButton ib_country_back;

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
        List<String> mData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mData.add("11月2" + i + "日 延期任务提醒");
        }
        onDataLoaded(mData);
    }

    @OnClick({R.id.ib_country_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_country_back:
                finish();
                break;
        }
    }

    @Override
    public void requestData(boolean needRefresh) {
        List<String> mData = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            mData.add("11月2" + i + "日 延期任务提醒");
        }
        onDataLoaded(mData);
    }

    @Override
    protected BaseListAdapter<String> getListAdapter() {
        return new TaskNoticeListAdapter(this);
    }

    @Override
    protected ListSettings getBaseSettings() {
        ListSettings settings = new ListSettings();
        settings.setCustomLayoutId(R.layout.activity_task_notice_list);
        settings.setCanLoadMore(false);
        settings.setCanRefresh(true);
        return settings;
    }
}
