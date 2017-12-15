package com.geobim.teamtask.activity;

import android.os.Bundle;
import android.view.View;

import com.geobim.teamtask.R;
import com.geobim.teamtask.base.ui.BaseLayoutActivity;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * 评论页面
 */
public class CommentActivity extends BaseLayoutActivity {

    @Override
    protected void initVariables() {}

    @Override
    protected void initViews(Bundle savedInstanceState) {}

    @Override
    protected void loadData() {}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        StatusBarUtil.setTranslucent(this, 0);//状态栏半透明;
        navigation.setBackgroundResource(R.drawable.img_title_bar);
        initTitle(R.drawable.icon_aleft, "评论");
        setTitleRight(0,"发送");
//        viewToolbarShadow.setVisibility(View.VISIBLE);
        ButterKnife.bind(this);
        layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
