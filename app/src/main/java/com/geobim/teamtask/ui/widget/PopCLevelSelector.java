package com.geobim.teamtask.ui.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.geobim.teamtask.R;

import java.util.List;

/**
 * 下拉menu
 */
public class PopCLevelSelector {
    private static PopCLevelSelector popCalendarmenu;
    private PopupWindow pop;

    public static PopCLevelSelector getInstance() {
        if (popCalendarmenu == null) {
            popCalendarmenu = new PopCLevelSelector();
        }
        return popCalendarmenu;
    }

    //下拉菜单
    public PopupWindow pop(final Activity activity, final List<String> list,
                           TextView layout) {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        } else {
            View menuPopLayout = activity.getLayoutInflater().inflate(
                    R.layout.pop_menulists, null);
            ListView menuListview = (ListView) menuPopLayout
                    .findViewById(R.id.menulist);
            PopAdapter listAdapter = new PopAdapter(activity, list);
            menuListview.setAdapter(listAdapter);
            // 点击listview中item的处理
            menuListview
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0,
                                                View arg1, int arg2, long arg3) {
                            if (mListener != null) {
                                mListener.onLevelItemClick(list.get(arg2));
                            }
                            pop.dismiss();
                        }
                    });

            pop = new PopupWindow(menuPopLayout, layout.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            ColorDrawable cd = new ColorDrawable(0);
            pop.setBackgroundDrawable(cd);
            pop.setAnimationStyle(R.style.PopupAnimation);
            pop.update();
            pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            pop.setTouchable(true); // 设置popupwindow可点击
            pop.setOutsideTouchable(true); // 设置popupwindow外部可点击
            pop.setFocusable(true); // 获取焦点

            // 设置popupwindow的位置
            int topBarHeight = layout.getBottom();
            pop.showAsDropDown(layout, 0,
                    (topBarHeight - layout.getHeight()) / 2);
            pop.setTouchInterceptor(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            // 点击了popupwindow的外部，popupwindow消失
                                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                                pop.dismiss();
                                                return true;
                                            }
                                            return false;
                                        }
                                    }
            );
        }
        return pop;
    }

    private OnLevelPopItemClickListener mListener;

    public void setOnItemClickListener(OnLevelPopItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnLevelPopItemClickListener {
        void onLevelItemClick(String levelBean);
    }

    class PopAdapter extends BaseAdapter {

        private List<String> list;
        private LayoutInflater inflater;

        public PopAdapter(Activity activity, List<String> list) {
            this.list = list;
            this.inflater = activity.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.pop_menuitem, null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.menuitem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(list.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}
