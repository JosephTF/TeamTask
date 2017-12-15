package com.geobim.teamtask.ui.PopupWindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.geobim.teamtask.R;

/**
 * 排序Pop
 */
public class PopSorting implements View.OnClickListener {
    public static PopupWindow pop;
    private View layoutway;
    private TextView layout_a, layout_b, layout_c, layout_d;
    private Activity mActivity;
    private LinearLayout mLinearLayout;
    private LinearLayout mLayout_shuffling;
    private View mBack;
    private TextView mSorting;

    public PopSorting(final Activity activity, LinearLayout layout, LinearLayout layout_shuffling, View back, TextView sorting) {
        this.mActivity = activity;
        this.mLinearLayout = layout;
        this.mLayout_shuffling = layout_shuffling;
        this.mSorting = sorting;
        this.mBack = back;
        this.mBack.setOnClickListener(this);
    }

    //下拉菜单
    public PopupWindow show(int item) {
        if (pop != null && pop.isShowing()) {
            mSorting.setTextColor(Color.parseColor("#7d7d7d"));
            setDrawable(mSorting, R.drawable.sort_default_icon);
            pop.dismiss();
            mBack.setVisibility(View.GONE);
        } else {
            mSorting.setTextColor(Color.parseColor("#49ceff"));
            setDrawable(mSorting, R.drawable.sort_press_icon);
            mBack.setVisibility(View.VISIBLE);
            layoutway = mActivity.getLayoutInflater().inflate(
                    R.layout.popwindow_sorting, null);
            pop = new PopupWindow(layoutway, mLinearLayout.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layout_a =  layoutway.findViewById(R.id.layout_a);
            layout_b = layoutway.findViewById(R.id.layout_b);
            layout_c =  layoutway.findViewById(R.id.layout_c);
            layout_d = layoutway.findViewById(R.id.layout_d);
            getItem(item);

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
            mSorting.setTextColor(Color.parseColor("#7d7d7d"));
            setDrawable(mSorting, R.drawable.sort_default_icon);
            mBack.setVisibility(View.GONE);
        }
    }


    public void setOnItemClick(final OnClicks clicks) {
        for (int i = 0; i < 4; i++) {
            final int items = i;
            switch (i) {
                case 0:
                    layout_a.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicks.onClick(v, items);
                        }
                    });
                    break;
                case 1:
                    layout_b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicks.onClick(v, items);
                        }
                    });
                    break;
                case 2:
                    layout_c.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicks.onClick(v, items);
                        }
                    });
                    break;
                case 3:
                    layout_d.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicks.onClick(v, items);
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                dismissPop();
                break;
        }
    }

    public interface OnClicks {
        public void onClick(View v, int item);
    }

    public void setOnBackClick(final OnBackClicks clicks) {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks.onClick(v);
            }
        });
    }

    public interface OnBackClicks {
        public void onClick(View v);
    }

    private void getItem(int mItem) {
        Drawable drawable = mActivity.getResources().getDrawable(R.drawable.isclickable_icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        switch (mItem) {
            case 0:
                layout_a.setTextColor(Color.parseColor("#fe850f"));
                layout_a.setCompoundDrawables(null, null, drawable, null);
                break;
            case 1:
                layout_b.setTextColor(Color.parseColor("#fe850f"));
                layout_b.setCompoundDrawables(null, null, drawable, null);
                break;
            case 2:
                layout_c.setTextColor(Color.parseColor("#fe850f"));
                layout_c.setCompoundDrawables(null, null, drawable, null);
                break;
            case 3:
                layout_d.setTextColor(Color.parseColor("#fe850f"));
                layout_d.setCompoundDrawables(null, null, drawable, null);
                break;
        }
    }

    /**
     * 左设置图标
     *
     * @param textView
     * @param resourcesId
     */
    public void setDrawable(TextView textView, int resourcesId) {
        Drawable drawable = mActivity.getResources().getDrawable(resourcesId);
        drawable.setBounds(10, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }
}
