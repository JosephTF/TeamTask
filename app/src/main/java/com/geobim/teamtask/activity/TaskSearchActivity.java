package com.geobim.teamtask.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.geobim.teamtask.R;
import com.geobim.teamtask.adapter.SearchTaskAdapter;
import com.geobim.teamtask.base.adapter.BaseListAdapter;
import com.geobim.teamtask.base.ui.BaseListActivity;
import com.geobim.teamtask.base.utils.ListSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * 搜索任务
 */
public class TaskSearchActivity extends BaseListActivity<String> implements View.OnClickListener {

    @Bind(R.id.fb_search)
    FancyButton fb_search;
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
        initData(10);
    }

    private void initData(int count) {
        List<String> mData = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            mData.add("任务" + i);
        }
        onDataLoaded(mData);
    }

    @Override
    public void requestData(boolean needRefresh) {
        initData(1);
    }


    @Override
    protected BaseListAdapter<String> getListAdapter() {
        return new SearchTaskAdapter(this);
    }

    @Override
    protected ListSettings getBaseSettings() {
        ListSettings settings = new ListSettings();
        settings.setCustomLayoutId(R.layout.head_search_task);
        settings.setCanLoadMore(false);
        settings.setCanRefresh(true);
        return settings;
    }

    @OnClick({R.id.fb_search,R.id.ib_country_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_search:
                initData(3);
                break;
            case R.id.ib_country_back:
                finish();
                break;
        }
    }
}
