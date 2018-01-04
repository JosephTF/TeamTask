package com.geobim.teamtask.ui.PopupWindow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.activity.SelDateActivity;
import com.geobim.teamtask.activity.TaskFounderActivity;

/**
 * 筛选Pop
 */
public class PopScreening implements View.OnClickListener {
    public static PopupWindow pop;
    private View layoutway;
    private RelativeLayout rl_finish,rlcance_view;
    private TextView tv_date_more,tv_team,tv_person,tv_founder;
    private Activity mActivity;
    private LinearLayout mLinearLayout;
    private LinearLayout mLayout_shuffling;
    private View mBack;
    private TextView mSorting;
    private TextView tv_type1,tv_type2,tv_type3,tv_type4;
    private TextView tv_states1,tv_states2,tv_states3,tv_states4;
    private TextView tv_date1,tv_date2,tv_date3,tv_date4,tv_date5;

    public PopScreening(final Activity activity, LinearLayout layout, LinearLayout layout_shuffling, View back, TextView sorting) {
        this.mActivity = activity;
        this.mLinearLayout = layout;
        this.mLayout_shuffling = layout_shuffling;
        this.mSorting = sorting;
        this.mBack = back;
        this.mBack.setOnClickListener(this);
    }

    //下拉菜单
    public PopupWindow show() {
        initView();
        if (pop != null && pop.isShowing()) {
            mSorting.setTextColor(Color.parseColor("#333333"));
            pop.dismiss();
            mBack.setVisibility(View.GONE);
        } else {
            mSorting.setTextColor(Color.parseColor("#303F9F"));
            mBack.setVisibility(View.VISIBLE);

            pop = new PopupWindow(layoutway, mLinearLayout.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            pop.setBackgroundDrawable(new ColorDrawable(0));
            pop.setAnimationStyle(R.style.PopupAnimation);
            pop.update();
            pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            pop.setTouchable(true); // 设置popupwindow可点击

            // 设置popupwindow的位置
            int topBarHeight = mLinearLayout.getBottom();
            pop.showAsDropDown(mLinearLayout, 0,
                    (topBarHeight - (mLinearLayout.getHeight() + mLayout_shuffling.getHeight())) / 50);
        }
        return pop;
    }

    private void initView(){
        layoutway = mActivity.getLayoutInflater().inflate(
                R.layout.popwindow_screening, null);

        rl_finish =   layoutway.findViewById(R.id.rl_finish);
        rlcance_view = layoutway.findViewById(R.id.rlcance_view);
        tv_date_more = layoutway.findViewById(R.id.tv_date_more);
        tv_team = layoutway.findViewById(R.id.tv_team);
        tv_founder = layoutway.findViewById(R.id.tv_founder);
        tv_person = layoutway.findViewById(R.id.tv_person);
        rl_finish.setOnClickListener(this);
        rlcance_view.setOnClickListener(this);
        tv_date_more.setOnClickListener(this);
        tv_team.setOnClickListener(this);
        tv_founder.setOnClickListener(this);
        tv_person.setOnClickListener(this);

        tv_type1 = layoutway.findViewById(R.id.tv_type1);
        tv_type2 = layoutway.findViewById(R.id.tv_type2);
        tv_type3 = layoutway.findViewById(R.id.tv_type3);
        tv_type4 = layoutway.findViewById(R.id.tv_type4);
        tv_type1.setOnClickListener(this);
        tv_type2.setOnClickListener(this);
        tv_type3.setOnClickListener(this);
        tv_type4.setOnClickListener(this);

        tv_states1 = layoutway.findViewById(R.id.tv_states1);
        tv_states2 = layoutway.findViewById(R.id.tv_states2);
        tv_states3 = layoutway.findViewById(R.id.tv_states3);
        tv_states4 = layoutway.findViewById(R.id.tv_states4);
        tv_states1.setOnClickListener(this);
        tv_states2.setOnClickListener(this);
        tv_states3.setOnClickListener(this);
        tv_states4.setOnClickListener(this);

        tv_date1 = layoutway.findViewById(R.id.tv_date1);
        tv_date2 = layoutway.findViewById(R.id.tv_date2);
        tv_date3 = layoutway.findViewById(R.id.tv_date3);
        tv_date4 = layoutway.findViewById(R.id.tv_date4);
        tv_date5 = layoutway.findViewById(R.id.tv_date5);
        tv_date1.setOnClickListener(this);
        tv_date2.setOnClickListener(this);
        tv_date3.setOnClickListener(this);
        tv_date4.setOnClickListener(this);
        tv_date5.setOnClickListener(this);
    }

    public boolean isShow() {
        if (pop != null && pop.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public void dismissPop() {
        if (pop == null) {
            return;
        }
        if (pop.isShowing()) {
            pop.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_finish:
                dismissPop();
                mBack.setVisibility(View.GONE);
                break;
            case R.id.rlcance_view:
                dismissPop();
                mBack.setVisibility(View.GONE);
                break;
            case R.id.tv_date_more:
                mActivity.startActivity(new Intent(mActivity, SelDateActivity.class));
                break;
            case R.id.tv_team:
                mActivity.startActivity(new Intent(mActivity, TaskFounderActivity.class));
                break;
            case R.id.tv_person:
                mActivity.startActivity(new Intent(mActivity, TaskFounderActivity.class));
                break;
            case R.id.tv_founder:
                mActivity.startActivity(new Intent(mActivity, TaskFounderActivity.class));
                break;
            case R.id.tv_type1:
                tv_type1.setTextColor(Color.parseColor("#ffffff"));
                tv_type2.setTextColor(Color.parseColor("#333333"));
                tv_type3.setTextColor(Color.parseColor("#333333"));
                tv_type4.setTextColor(Color.parseColor("#333333"));
                tv_type1.setBackgroundResource(R.drawable.bg_round);
                tv_type2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_type3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_type4.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_type2:
                tv_type1.setTextColor(Color.parseColor("#333333"));
                tv_type2.setTextColor(Color.parseColor("#ffffff"));
                tv_type3.setTextColor(Color.parseColor("#333333"));
                tv_type4.setTextColor(Color.parseColor("#333333"));
                tv_type1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_type2.setBackgroundResource(R.drawable.bg_round);
                tv_type3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_type4.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_type3:
                tv_type1.setTextColor(Color.parseColor("#333333"));
                tv_type2.setTextColor(Color.parseColor("#333333"));
                tv_type3.setTextColor(Color.parseColor("#ffffff"));
                tv_type4.setTextColor(Color.parseColor("#333333"));
                tv_type1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_type2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_type3.setBackgroundResource(R.drawable.bg_round);
                tv_type4.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_type4:
                tv_type1.setTextColor(Color.parseColor("#333333"));
                tv_type2.setTextColor(Color.parseColor("#333333"));
                tv_type3.setTextColor(Color.parseColor("#333333"));
                tv_type4.setTextColor(Color.parseColor("#ffffff"));
                tv_type1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_type2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_type3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_type4.setBackgroundResource(R.drawable.bg_round);
                break;
            case R.id.tv_states1:
                tv_states1.setTextColor(Color.parseColor("#ffffff"));
                tv_states2.setTextColor(Color.parseColor("#333333"));
                tv_states3.setTextColor(Color.parseColor("#333333"));
                tv_states4.setTextColor(Color.parseColor("#333333"));
                tv_states1.setBackgroundResource(R.drawable.bg_round);
                tv_states2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_states3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_states4.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_states2:
                tv_states1.setTextColor(Color.parseColor("#333333"));
                tv_states2.setTextColor(Color.parseColor("#ffffff"));
                tv_states3.setTextColor(Color.parseColor("#333333"));
                tv_states4.setTextColor(Color.parseColor("#333333"));
                tv_states1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_states2.setBackgroundResource(R.drawable.bg_round);
                tv_states3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_states4.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_states3:
                tv_states1.setTextColor(Color.parseColor("#333333"));
                tv_states2.setTextColor(Color.parseColor("#333333"));
                tv_states3.setTextColor(Color.parseColor("#ffffff"));
                tv_states4.setTextColor(Color.parseColor("#333333"));
                tv_states1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_states2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_states3.setBackgroundResource(R.drawable.bg_round);
                tv_states4.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_states4:
                tv_states1.setTextColor(Color.parseColor("#333333"));
                tv_states2.setTextColor(Color.parseColor("#333333"));
                tv_states3.setTextColor(Color.parseColor("#333333"));
                tv_states4.setTextColor(Color.parseColor("#ffffff"));
                tv_states1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_states2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_states3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_states4.setBackgroundResource(R.drawable.bg_round);
                break;
            case R.id.tv_date1:
                tv_date1.setTextColor(Color.parseColor("#ffffff"));
                tv_date2.setTextColor(Color.parseColor("#333333"));
                tv_date3.setTextColor(Color.parseColor("#333333"));
                tv_date4.setTextColor(Color.parseColor("#333333"));
                tv_date5.setTextColor(Color.parseColor("#333333"));
                tv_date1.setBackgroundResource(R.drawable.bg_round);
                tv_date2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date4.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date5.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_date2:
                tv_date1.setTextColor(Color.parseColor("#333333"));
                tv_date2.setTextColor(Color.parseColor("#ffffff"));
                tv_date3.setTextColor(Color.parseColor("#333333"));
                tv_date4.setTextColor(Color.parseColor("#333333"));
                tv_date5.setTextColor(Color.parseColor("#333333"));
                tv_date1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date2.setBackgroundResource(R.drawable.bg_round);
                tv_date3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date4.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date5.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_date3:
                tv_date1.setTextColor(Color.parseColor("#333333"));
                tv_date2.setTextColor(Color.parseColor("#333333"));
                tv_date3.setTextColor(Color.parseColor("#ffffff"));
                tv_date4.setTextColor(Color.parseColor("#333333"));
                tv_date5.setTextColor(Color.parseColor("#333333"));
                tv_date1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date3.setBackgroundResource(R.drawable.bg_round);
                tv_date4.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date5.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_date4:
                tv_date1.setTextColor(Color.parseColor("#333333"));
                tv_date2.setTextColor(Color.parseColor("#333333"));
                tv_date3.setTextColor(Color.parseColor("#333333"));
                tv_date4.setTextColor(Color.parseColor("#ffffff"));
                tv_date5.setTextColor(Color.parseColor("#333333"));
                tv_date1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date4.setBackgroundResource(R.drawable.bg_round);
                tv_date5.setBackgroundResource(R.drawable.bg_round_white_gray);
                break;
            case R.id.tv_date5:
                tv_date1.setTextColor(Color.parseColor("#333333"));
                tv_date2.setTextColor(Color.parseColor("#333333"));
                tv_date3.setTextColor(Color.parseColor("#333333"));
                tv_date4.setTextColor(Color.parseColor("#333333"));
                tv_date5.setTextColor(Color.parseColor("#ffffff"));
                tv_date1.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date2.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date3.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date4.setBackgroundResource(R.drawable.bg_round_white_gray);
                tv_date5.setBackgroundResource(R.drawable.bg_round);
                break;
        }
    }
}
