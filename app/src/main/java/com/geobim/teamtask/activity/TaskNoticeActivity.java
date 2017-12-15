package com.geobim.teamtask.activity;

import android.os.Bundle;
import android.view.View;

import com.geobim.teamtask.R;
import com.geobim.teamtask.adapter.TaskNoticeListAdapter;
import com.geobim.teamtask.base.adapter.BaseListAdapter;
import com.geobim.teamtask.base.ui.BaseListActivity;
import com.geobim.teamtask.base.utils.ListSettings;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 任务通知
 */
public class TaskNoticeActivity extends BaseListActivity<String> {

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
        viewToolbarShadow.setVisibility(View.GONE);
        navigation.setBackgroundResource(R.drawable.background);
        initTitle(R.drawable.icon_aleft, "通知");
        StatusBarUtil.setTranslucent(this, 0);//状态栏半透明

        List<String> mData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mData.add("11月2" + i + "日 延期任务提醒");
        }
        onDataLoaded(mData);
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
