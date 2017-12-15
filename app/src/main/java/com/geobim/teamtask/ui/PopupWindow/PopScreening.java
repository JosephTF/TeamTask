package com.geobim.teamtask.ui.PopupWindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geobim.teamtask.R;

/**
 * 筛选Pop
 */
public class PopScreening implements View.OnClickListener {
    public static PopupWindow pop;
    private View layoutway;
    private RelativeLayout rl_finish,rlcance_view;
    private TextView tv_date_more;
    private Activity mActivity;
    private LinearLayout mLinearLayout;
    private LinearLayout mLayout_shuffling;
    private View mBack;
    private TextView mSorting;

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
        if (pop != null && pop.isShowing()) {
            mSorting.setTextColor(Color.parseColor("#7d7d7d"));
            pop.dismiss();
            mBack.setVisibility(View.GONE);
        } else {
            mSorting.setTextColor(Color.parseColor("#49ceff"));
            mBack.setVisibility(View.VISIBLE);
            layoutway = mActivity.getLayoutInflater().inflate(
                    R.layout.popwindow_screening, null);
            pop = new PopupWindow(layoutway, mLinearLayout.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rl_finish =   layoutway.findViewById(R.id.rl_finish);
            rl_finish.setOnClickListener(this);
            rlcance_view = layoutway.findViewById(R.id.rlcance_view);
            rlcance_view.setOnClickListener(this);
            tv_date_more = layoutway.findViewById(R.id.tv_date_more);
            tv_date_more.setOnClickListener(this);
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
                dismissPop();
                mBack.setVisibility(View.GONE);
                break;
        }
    }
}
