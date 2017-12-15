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
public class SearchTaskAdapter extends BaseListAdapter<String> {

    public SearchTaskAdapter(Context context) {
        super(context, R.layout.item_search_task);
    }

    @Override
    protected void onBindContentViewData(BaseViewHolder helper, String item, final int position) {
        helper.getView(R.id.rlview3).setOnClickListener(new View.OnClickListener() {
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
        TextView tv_type = (TextView) holder.getView(R.id.text_view);
        tv_type.setText("任务+"+1);
    }
}
