package com.geobim.teamtask.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.geobim.teamtask.R;
import com.geobim.teamtask.base.ui.BaseLayoutActivity;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评论页面
 */
public class CommentActivity extends BaseLayoutActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        StatusBarUtil.setTranslucent(this, 0);//状态栏半透明;
        ButterKnife.bind(this);
        layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.ib_country_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_country_back:
                finish();
                break;
        }
    }
}
