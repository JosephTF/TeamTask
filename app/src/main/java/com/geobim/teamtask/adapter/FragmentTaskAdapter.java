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
 * Created by 19659 on 2015/10/28.
 */
public class FragmentTaskAdapter extends BaseListAdapter<String> {
    TextView tv_Task_Date_Title;

    public FragmentTaskAdapter(Context context) {
        super(context, R.layout.calendar_item2);
    }

    @Override
    protected void onBindContentViewData(BaseViewHolder helper, String item, final int position) {
        helper.getView(R.id.rl_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, TaskDetailActivity.class));
            }
        });
        init(helper, item, position);
    }

    @Override
    protected void onBindHeadViewData(BaseViewHolder helper) {
    }

    private void init(BaseViewHolder holder, String item, int position) {
        tv_Task_Date_Title = (TextView) holder.getView(R.id.tv_title);
        if (position == 0) {
            tv_Task_Date_Title.setVisibility(View.VISIBLE);
            tv_Task_Date_Title.setText(item);
        }
        TextView tv_type = (TextView) holder.getView(R.id.tv_task_name);
        tv_type.setText(item + "任务" + position);
    }
}