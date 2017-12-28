package com.geobim.teamtask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.geobim.teamtask.R;
import com.geobim.teamtask.fragment.FragmentMe;
import com.geobim.teamtask.fragment.FragmentModel;
import com.geobim.teamtask.fragment.FragmentProject;
import com.geobim.teamtask.fragment.FragmentTask;
import com.geobim.teamtask.ui.widget.bottombar.BottomBarManager;
import com.geobim.teamtask.util.ActivityList;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import java.lang.reflect.Field;

/**
 * 主界面
 * Created by Administrator on 2017/11/27.
 */

public class MainActivity extends BaseActivity{
    private static final String TAB_TASK_FRAGMENT = "task";
    private static final String TAB_PROJECT_FRAGMENT = "project";
    private static final String TAB_MODEL_FRAGMENT = "model";
    private static final String TAB_ME_FRAGMENT = "me";

    private FragmentMe mFragmentMe;
    private FragmentModel mFragmentModel;
    private FragmentProject mFragmentProject;
    private FragmentTask mFragmentTask;
    private FragmentManager mFragmentManager;
    private RelativeLayout rl_task, rl_project, rl_model, rl_me;
    private ImageView iv_quick;
    private LinearLayout ll_bottombar;
    private FragmentTransaction mTransaction;
    private BottomBarManager mBarManager;

    private String mCurrentIndex;
    private boolean mComeFromAccoutActivity;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucent(MainActivity.this, 0);//状态栏半透明
        setSwipeBackEnable(false);
        rl_task = (RelativeLayout) findViewById(R.id.rl_main_task);
        rl_project = (RelativeLayout) findViewById(R.id.rl_main_project);
        rl_model = (RelativeLayout) findViewById(R.id.rl_main_model);
        rl_me = (RelativeLayout) findViewById(R.id.rl_main_me);
        iv_quick = (ImageView) findViewById(R.id.iv_main_quick);
        ll_bottombar = (LinearLayout) findViewById(R.id.ll_main_bottombar);
        initTab();
        initListener();
        //如果是从崩溃中恢复，还需要加载之前的缓存
        if (savedInstanceState != null) {
            restoreFragment(savedInstanceState);
        } else {
            setTabFragment(TAB_TASK_FRAGMENT);
        }
    }

    @Override
    protected void loadData() {
        mComeFromAccoutActivity = getIntent().getBooleanExtra("comeFromAccoutActivity", false);
    }

    private void initTab() {
        //LogReport.getInstance().upload(mContext);
        mBarManager = new BottomBarManager();
        mBarManager.showBottomBar(rl_task);
        mFragmentManager = getSupportFragmentManager();
    }



    private void initListener() {
        rl_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(TAB_TASK_FRAGMENT);
            }
        });
        rl_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(TAB_PROJECT_FRAGMENT);
            }
        });
        iv_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuickCreateActvity.class);
                startActivity(intent);
            }
        });

        rl_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(TAB_MODEL_FRAGMENT);
            }
        });

        rl_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(TAB_ME_FRAGMENT);
            }
        });
    }

    /**
     * 如果fragment因为内存不够或者其他原因被销毁掉，在这个方法中执行恢复操作
     */
    private void restoreFragment(Bundle savedInstanceState) {
        mCurrentIndex = savedInstanceState.getString("index");
        mFragmentTask = (FragmentTask) mFragmentManager.findFragmentByTag(TAB_TASK_FRAGMENT);
        mFragmentProject = (FragmentProject) mFragmentManager.findFragmentByTag(TAB_PROJECT_FRAGMENT);
        mFragmentModel = (FragmentModel) mFragmentManager.findFragmentByTag(TAB_MODEL_FRAGMENT);
        mFragmentMe = (FragmentMe) mFragmentManager.findFragmentByTag(TAB_ME_FRAGMENT);
        switchToFragment(mCurrentIndex);
    }

    /**
     * Activity被销毁的时候，要记录当前处于哪个页面
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("index", mCurrentIndex);
        super.onSaveInstanceState(outState);
    }

    /**
     * 执行切换fragment 的操作
     * 注意：
     * 1. 切换页面的时候，还要调用showBottomBar来保证底部导航栏的显示
     *
     * @param index
     */
    private void switchToFragment(String index) {
        ll_bottombar.clearAnimation();
        ll_bottombar.setVisibility(View.VISIBLE);
        mTransaction = mFragmentManager.beginTransaction();
        hideAllFragments(mTransaction);
        switch (index) {
            case TAB_TASK_FRAGMENT:
                showFragmentTask();
                break;
            case TAB_PROJECT_FRAGMENT:
                showFragmentProject();
                break;
            case TAB_MODEL_FRAGMENT:
                showFragmentModel();
                break;
            case TAB_ME_FRAGMENT:
                showFragmentMe();
                break;
        }
        mCurrentIndex = index;
        mTransaction.commit();
    }

    /**
     * 切换到首页模块
     */
    private void showFragmentTask() {
        rl_task.setSelected(true);
        if (mFragmentTask == null) {
            mFragmentTask = mFragmentTask.newInstance(mComeFromAccoutActivity);
            mTransaction.add(R.id.fl_main_content, mFragmentTask, TAB_TASK_FRAGMENT);
        } else {
            mTransaction.show(mFragmentTask);
//            if (mCurrentIndex.equals(TAB_TASK_FRAGMENT) && mFragmentTask != null && mFragmentTask.rvToDoList != null) {
//               mFragmentTask.scrollToTop(false);
//            }
        }
    }

    /**
     * 切换到消息模块
     */
    private void showFragmentProject() {
        rl_project.setSelected(true);
        if (mFragmentProject == null) {
            mFragmentProject = new FragmentProject();
            mTransaction.add(R.id.fl_main_content, mFragmentProject, TAB_PROJECT_FRAGMENT);
        } else {
            mTransaction.show(mFragmentProject);
        }
    }

    /**
     * 切换到模型模块
     */
    private void showFragmentModel() {
        rl_model.setSelected(true);
        if (mFragmentModel == null) {
            mFragmentModel = new FragmentModel();
            mTransaction.add(R.id.fl_main_content, mFragmentModel, TAB_MODEL_FRAGMENT);
        } else {
            mTransaction.show(mFragmentModel);
        }
    }

    /**
     * 切换到个人中心模块
     */
    private void showFragmentMe() {
        rl_me.setSelected(true);
        if (mFragmentMe == null) {
            mFragmentMe = FragmentMe.newInstance();
            mTransaction.add(R.id.fl_main_content, mFragmentMe, TAB_ME_FRAGMENT);
        } else {
            mTransaction.show(mFragmentMe);

        }
    }


    /**
     * 显示指定的fragment，并且把对应的导航栏的icon设置成高亮状态
     * 注意：
     * 1. 如果选项卡已经位于当前页，则执行其他操作
     *
     * @param tabName 需要切换到的具体页面
     */
    private void setTabFragment(String tabName) {
        if (mFragmentTask != null) {
            mBarManager.showBottomBar(rl_task);
        }

        if (!tabName.equals(mCurrentIndex)) {
            switchToFragment(tabName);
        } else {
            alreadyAtFragment(mCurrentIndex);
        }
    }

    /**
     * 如果选项卡已经位于当前页
     * 1. 对于首页fragment，执行：滑动到顶部，并且刷新时间线，获取最新微博
     * 2. 对于消息fragment，执行：无
     * 3. 对于发现fragment，执行：无
     * 4. 对于关于我fragment，执行：无
     *
     * @param currentIndex
     */
    private void alreadyAtFragment(String currentIndex) {
        //如果在当前页
        switch (currentIndex) {
            case TAB_TASK_FRAGMENT:
//                if (mFragmentTask != null) {
//                    mFragmentTask.scrollToTop(true);
//                }
                break;
            case TAB_PROJECT_FRAGMENT:
                break;
            case TAB_MODEL_FRAGMENT:
                break;
            case TAB_ME_FRAGMENT:
                break;
        }
    }


    /**
     * 隐藏所有的fragment，并且取消所有的底部导航栏的icon的高亮状态
     *
     * @param transaction
     */
    private void hideAllFragments(FragmentTransaction transaction) {
        if (mFragmentTask != null) {
            transaction.hide(mFragmentTask);
        }
        if (mFragmentProject != null) {
            transaction.hide(mFragmentProject);
        }

        if (mFragmentModel != null) {
            transaction.hide(mFragmentModel);
        }
        if (mFragmentMe != null) {
            transaction.hide(mFragmentMe);
        }
        rl_task.setSelected(false);
        rl_project.setSelected(false);
        rl_model.setSelected(false);
        rl_me.setSelected(false);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFragmentTask != null) {
            mFragmentTask.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 解决输入法中的内存泄漏问题
     */
    public void fixInputMethodManagerLeak(Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == context) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        Log.i("Context is not suitable", "get_context=" + v_get.getContext() + " dest_context=" + context);
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fixInputMethodManagerLeak(this);
    }
}
