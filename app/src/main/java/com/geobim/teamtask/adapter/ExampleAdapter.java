package com.geobim.teamtask.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.activity.TaskDetailActivity;

/**
 * Created by ldf on 17/6/14.
 */

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    public static Context context;
    private String[] titles;

    public ExampleAdapter(Context context) {
        titles = context.getResources().getStringArray(R.array.titles);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ExampleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.calendar_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RelativeLayout rlView;

        ViewHolder(View view) {
            super(view);
            textView =  view.findViewById(R.id.text_view);
            rlView =  view.findViewById(R.id.rlview1);
            rlView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   context.startActivity(new Intent(context, TaskDetailActivity.class));
                }
            });
        }
    }
}
