package com.geobim.teamtask.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.ui.DateWheelView.WheelStyle;
import com.geobim.teamtask.ui.DateWheelView.WheelView;
import com.geobim.teamtask.util.SizeConvertUtil;

import java.util.Calendar;

/**
 * 日期选择对话框
 * <p>
 * Created by siqi on 2017/12/25.
 */
public class SelectDateDialog extends BaseDialog {

    private View dialogView;
    private WheelView yearWheel;
    private WheelView monthWheel;
    private WheelView dayWheel;

    int selectYear;
    int selectMonth;

    private OnClickListener onClickListener;

    /**
     * 创建一个日期选择对话框
     *
     * @param mContext
     */
    public SelectDateDialog(Context mContext) {
        this.context = mContext;
        create();
    }

    /**
     * 创建一个日期选择对话框
     *
     * @param mContext
     */
    public SelectDateDialog(Context mContext, OnClickListener listener) {
        this.context = mContext;
        onClickListener = listener;
        create();
    }

    /**
     * 创建选择日期对话框
     */
    private void create() {

        if (dialog != null) {
            return;
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        dialogView = layoutInflater.inflate(R.layout.dialog_wheel_select_date, null);
        yearWheel = dialogView.findViewById(R.id.select_date_wheel_year_wheel);
        monthWheel = dialogView.findViewById(R.id.select_date_month_wheel);
        dayWheel =  dialogView.findViewById(R.id.select_date_day_wheel);

        yearWheel.setWheelStyle(WheelStyle.STYLE_YEAR);
        yearWheel.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                selectYear = index + WheelStyle.minYear;
                dayWheel.setWheelItemList(WheelStyle.createDayString(selectYear, selectMonth));
            }
        });

        monthWheel.setWheelStyle(WheelStyle.STYLE_MONTH);
        monthWheel.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                selectMonth = index + 1;
                dayWheel.setWheelItemList(WheelStyle.createDayString(selectYear, selectMonth));
            }
        });

        TextView cancelBt = (TextView) dialogView.findViewById(R.id.select_date_cancel);
        cancelBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    if (!onClickListener.onCancel()) {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
        TextView sureBt = (TextView) dialogView.findViewById(R.id.select_date_sure);
        sureBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int year = yearWheel.getCurrentItem() + WheelStyle.minYear;
                int month = monthWheel.getCurrentItem();
                int day = dayWheel.getCurrentItem() + 1;
                int daySize = dayWheel.getItemCount();
                if (day > daySize) {
                    day = day - daySize;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, day);
                long setTime = calendar.getTimeInMillis();

                if (onClickListener != null) {
                    if (!onClickListener.onSure(year, month, day, setTime)) {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
        dialog = new AlertDialog.Builder(context).setView(dialogView).create();
    }

    /**
     * 显示选择日期对话框
     *
     * @param year  默认显示的年
     * @param month 默认月
     * @param day   默认日
     */
    public void show(int year, int month, int day) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        dayWheel.setWheelItemList(WheelStyle.createDayString(year - WheelStyle.minYear, month + 1));
        yearWheel.setCurrentItem(year - WheelStyle.minYear);
        monthWheel.setCurrentItem(month);
        dayWheel.setCurrentItem(day - 1);
        dialog.getWindow().setLayout(SizeConvertUtil.dpTopx(context, 300), SizeConvertUtil.dpTopx(context, 200));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 选择日期对话框回调
     *
     * @param listener
     */
    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    /**
     * 选择日期对话框回调接口，调用者实现
     *
     * @author huangzj
     */
    public interface OnClickListener {
        boolean onSure(int year, int month, int day, long time);

        boolean onCancel();
    }
}