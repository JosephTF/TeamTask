package com.geobim.teamtask.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.activity.TaskDetailActivity;
import com.geobim.teamtask.base.adapter.BaseListAdapter;
import com.geobim.teamtask.base.adapter.BaseViewHolder;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃永无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 */
public class TaskListAdapter extends BaseListAdapter<String> {

    public TaskListAdapter(Context context) {
        super(context, R.layout.task_item);
    }

    @Override
    protected void onBindContentViewData(BaseViewHolder helper, String item, int position) {
        TextView textView = (TextView) helper.getView(R.id.tv_task_name);
        textView.setText(mDatas.get(position));
        helper.getView(R.id.rlview1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, TaskDetailActivity.class));
            }
        });
    }

    @Override
    protected void onBindHeadViewData(BaseViewHolder helper) {
    }
}
