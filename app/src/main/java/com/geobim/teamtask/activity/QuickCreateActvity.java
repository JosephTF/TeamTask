package com.geobim.teamtask.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

/**
 * 快速创建Activity
 * Created by Administrator on 2017/12/26.
 */

public class QuickCreateActvity extends BaseActivity{
    private Context mContext;
    private ImageView composeIdea;
    private ImageView composePhoto;
    private ImageView composeHeadlines;
    private ImageView composeLbs;
    private ImageView composeReview;
    private ImageView composeMore;
    private ImageView composeClose;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_quickcreate);
        StatusBarUtil.setTranslucent(this, 0);//状态栏半透明
    }

    @Override
    protected void loadData() {

    }
}
