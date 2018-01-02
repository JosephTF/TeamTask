package com.geobim.teamtask.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geobim.teamtask.R;
import com.geobim.teamtask.activity.TaskListActivity;
import com.geobim.teamtask.activity.TaskNoticeActivity;
import com.geobim.teamtask.adapter.FragmentTaskAdapter;
import com.geobim.teamtask.base.adapter.BaseListAdapter;
import com.geobim.teamtask.base.ui.BaseListFragment;
import com.geobim.teamtask.base.utils.ListSettings;
import com.geobim.teamtask.entity.User;
import com.geobim.teamtask.ui.CalendarView.CustomDayView;
import com.geobim.teamtask.ui.widget.bottombar.BottomBarManager;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

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
public class FragmentTask extends BaseListFragment<String> implements View.OnClickListener{

    MonthPager monthPager;
    TextView textViewYearDisplay;
    TextView textViewMonthDisplay;
    TextView tv_back_today;
    TextView tv_notice;
    TextView tv_more;
    private RelativeLayout rl_topbar;   //顶部导航栏

    private CalendarDate currentDate;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private Context mContext;

    private int PAGE_INDEX = 1;
    private int PAGE_SIZE = 9;
    List<String> mData = new ArrayList<>();
    private User mCurrentUser;
    private onButtonBarListener mOnBottonBarListener;
    public FragmentTask() {
    }

    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     */
    public static FragmentTask newInstance(boolean comeFromAccoutActivity) {
        FragmentTask homeFragment = new FragmentTask();
        Bundle args = new Bundle();
        args.putBoolean("comeFromAccoutActivity", comeFromAccoutActivity);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    protected void OnViewCreated() {
        initView();
        initLoadData("2017年12月29日");
        initCurrentDate();
        initCalendarView();
    }
    private void initLoadData(String dateStr) {
        mData.clear();
        for (int i = 0; i < 5; i++) {
            mData.add(dateStr);
        }
        onDataLoaded(mData);
    }

    private void initView() {
        mContext = getContext();
        monthPager = mContentView.findViewById(R.id.calendar_view);
        textViewYearDisplay = mContentView.findViewById(R.id.show_year_view);
        textViewMonthDisplay = mContentView.findViewById(R.id.show_month_view);
        tv_back_today = mContentView.findViewById(R.id.back_today_button);
        tv_notice = mContentView.findViewById(R.id.tv_notice);
        tv_more = mContentView.findViewById(R.id.tv_more);
        rl_topbar = mContentView.findViewById(R.id.rl_task_topbar);
        tv_back_today.setOnClickListener(this);
        tv_notice.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        mViewBuilder.mRecyclerview.setHasFixedSize(true);
        mViewBuilder.mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //此处强行setViewHeight，日历牌的高度
        monthPager.setViewheight(Utils.dpi2px(getActivity(), 270));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_today_button:
                onClickBackToDayBtn();
                break;
            case R.id.tv_notice:
                startActivity(new Intent(mContext, TaskNoticeActivity.class));
                break;
            case R.id.tv_more:
                startActivity(new Intent(mContext, TaskListActivity.class));
                break;
        }
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
                initLoadData(date.toString());
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

    /**
     *  利用onDataLoaded提供数据到adapter显示
     * @param needRefresh 是否为下拉刷新
     */
    @Override
    public void requestData(boolean needRefresh) {
        if (needRefresh) {
            PAGE_INDEX = 1;
        }
        //onDataLoaded(mData);
    }

    private FragmentTaskAdapter myActivityAdapter;

    /**
     * 进入adapter
     * @return
     */
    @Override
    public BaseListAdapter<String> getListAdapter() {
        myActivityAdapter = new FragmentTaskAdapter(getActivity());
        return myActivityAdapter;
    }

    /**
     * fragment 配置项
     * @return
     */
    @Override
    protected ListSettings getBaseSettings() {
        ListSettings settings = new ListSettings();
        settings.setCustomLayoutId(R.layout.fragment_tack_list);
        settings.setCanLoadMore(true);
        settings.setCanRefresh(true);
        return settings;
    }

    /**
     * 把列表滑动到顶部，refreshDrata为true的话，会同时获取更新的数据
     *
     * @param refreshData

    public void scrollToTop(boolean refreshData) {
        rvToDoList.scrollToPosition(0);
        if (refreshData) {
            rvToDoList.post(new Runnable() {
                @Override
                public void run() {
                    //mHomePresent.pullToRefreshData(mCurrentGroup, mContext);
                }
            });
        }
    }*/

    /**
     * 隐藏底部导航栏
     */
    public void hideTopBar() {
        BottomBarManager barManager = new BottomBarManager();
        barManager.hideTopBar(rl_topbar,mContext);
    }


    /**
     * 显示顶部导航栏
     */
    public void showTopBar() {
        BottomBarManager barManager = new BottomBarManager();
        barManager.showTopBar(rl_topbar);
    }


    /**
     * 设置实现
     *
     * @param onBarListener
     */
    public void setOnBarListener(onButtonBarListener onBarListener) {
        this.mOnBottonBarListener = onBarListener;
    }


    /**
     * 因为ButotnBar的布局并不在fragment中，而是在MainActivity中，所有隐藏和显示底部导航栏的工作要交给MainActivity去做
     */
    public interface onButtonBarListener {
        void showButtonBar();

        void hideButtonBar();
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
