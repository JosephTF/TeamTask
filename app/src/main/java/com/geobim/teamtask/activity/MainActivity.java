package com.geobim.teamtask.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.geobim.teamtask.R;
import com.geobim.teamtask.fragment.FragmentModel;
import com.geobim.teamtask.fragment.FragmentMe;
import com.geobim.teamtask.fragment.FragmentProject;
import com.geobim.teamtask.fragment.FragmentQuick;
import com.geobim.teamtask.fragment.FragmentTask;
import com.geobim.teamtask.ui.FragmentView.TabLayoutFTSmk;
import com.geobim.teamtask.ui.FragmentView.TabLayoutSmk;
import com.geobim.teamtask.util.ActivityList;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面
 * Created by Administrator on 2017/11/27.
 */

public class MainActivity extends BaseActivity implements TabLayoutSmk.CallbackPosition{
    @Bind(R.id.tabLayout)
    TabLayoutFTSmk tabLayout;
    //标签切换
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private List<Integer> mIconUnselectIds = new ArrayList<>();
    private List<Integer> mIconSelectIds = new ArrayList<>();

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucent(MainActivity.this, 0);//状态栏半透明
        setSwipeBackEnable(false);
        ButterKnife.bind(this);
        initTab();
    }

    @Override
    protected void loadData() {

    }

    private void initTab() {
        fragments.add(new FragmentTask());
        fragments.add(new FragmentProject());
        fragments.add(new FragmentModel());
        fragments.add(new FragmentMe());
        mTitles.add("任务");
        mTitles.add("项目");
        mTitles.add("模型");
        mTitles.add("我的");
        mIconUnselectIds.add(R.drawable.tab_home_unselect);
        mIconUnselectIds.add(R.drawable.tab_speech_unselect);
        mIconUnselectIds.add(R.drawable.tab_contact_unselect);
        mIconUnselectIds.add(R.drawable.tab_more_unselect);
        mIconSelectIds.add(R.drawable.tab_home_select);
        mIconSelectIds.add(R.drawable.tab_speech_select);
        mIconSelectIds.add(R.drawable.tab_contact_select);
        mIconSelectIds.add(R.drawable.tab_more_select);
        tabLayout.init(fragments, mTitles, mIconUnselectIds, mIconSelectIds, getSupportFragmentManager(), this);
        tabLayout.setTextSelectColor(Color.parseColor("#228EFD"));
        tabLayout.setLongClickable(true);
    }

    @Override
    public void positon(int position) {

    }

    /**
     * 虚拟返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SweetAlertDialog sad = new SweetAlertDialog(this);
//            sd.setCancelable(true);
//            sd.setCanceledOnTouchOutside(true);
            sad.setTitleText("确认退出TeamTask吗？");
            sad.setConfirmText("退出");
            sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    ActivityList.getInstance().exit();
                }
            });
            sad.setCancelText("取消");
            sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
            sad.show();
        }
        return false;
    }
}
