package com.geobim.teamtask.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.geobim.teamtask.R;
import com.geobim.teamtask.activity.TaskListActivity;
import com.geobim.teamtask.activity.TaskNoticeActivity;
import com.geobim.teamtask.adapter.ExampleAdapter;
import com.geobim.teamtask.base.ui.BaseFragment;
import com.geobim.teamtask.ui.CalendarView.CustomDayView;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
 *  任务fragment
 */
public class FragmentTask extends BaseFragment {

    @Bind(R.id.content)
    CoordinatorLayout content;
    @Bind(R.id.show_year_view)
    TextView textViewYearDisplay;
    @Bind(R.id.show_month_view)
    TextView textViewMonthDisplay;
    @Bind(R.id.back_today_button)
    TextView backToday;
    @Bind(R.id.calendar_view)
    MonthPager monthPager;
    @Bind(R.id.list)
    RecyclerView rvToDoList;
    @Bind(R.id.scroll_switch)
    TextView scrollSwitch;
    @Bind(R.id.next_month)
    TextView nextMonthBtn;
    @Bind(R.id.last_month)
    TextView lastMonthBtn;
    @Bind(R.id.tv_more)
    TextView tv_more;
    @Bind(R.id.tv_tongzhi)
    TextView tv_tongzhi;

    private View view;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private Context mContext;
    private CalendarDate currentDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_task, null);
        ButterKnife.bind(this,view);
        mContentView.addView(view);
        StatusBarUtil.setTranslucent(getActivity(), 0);//状态栏半透明
        mContext = this.getContext();
        //此处强行setViewHeight，日历牌的高度
        monthPager.setViewheight(Utils.dpi2px(mContext, 270));
        rvToDoList.setHasFixedSize(true);
        //这里用线性显示 类似于listview
        rvToDoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvToDoList.setAdapter(new ExampleAdapter(mContext));
        initCurrentDate();
        initCalendarView();
        initToolbarClickListener();

        tv_tongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TaskNoticeActivity.class));
            }
        });

    }

    @OnClick({R.id.tv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                startActivity(new Intent(mContext, TaskListActivity.class));
                break;
        }
    }

    /**
     * 初始化对应功能的listener
     *
     */
    private void initToolbarClickListener() {
        backToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackToDayBtn();
            }
        });
        scrollSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendayType.WEEK) {
                    Utils.scrollTo(content, rvToDoList, monthPager.getViewHeight(), 200);
                    calendarAdapter.switchToMonth();
                } else {
                    Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
                    calendarAdapter.switchToWeek(monthPager.getRowIndex());
                }
            }
        });
    }

    /**
     * 初始化currentDate
     *
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        textViewYearDisplay.setText(currentDate.getYear() + "年");
        textViewMonthDisplay.setText(currentDate.getMonth() + "");
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     *
     */
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(mContext, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                mContext,
                onSelectDateListener,
                CalendarAttr.CalendayType.WEEK,
                customDayView);
        initMarkData();
        initMonthPager();
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     *
     */
    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        markData.put("2017-12-19", "1");
        markData.put("2017-12-20", "0");
        markData.put("2017-12-25", "1");
        markData.put("2017-12-26", "0");
        calendarAdapter.setMarkData(markData);
    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
                Toast.makeText(mContext, "点击查看" + date.toString() + "的任务", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
    }

    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        textViewYearDisplay.setText(date.getYear() + "年");
        textViewMonthDisplay.setText(date.getMonth() + "");
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) instanceof Calendar) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    textViewYearDisplay.setText(date.getYear() + "年");
                    textViewMonthDisplay.setText(date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        textViewYearDisplay.setText(today.getYear() + "年");
        textViewMonthDisplay.setText(today.getMonth() + "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
