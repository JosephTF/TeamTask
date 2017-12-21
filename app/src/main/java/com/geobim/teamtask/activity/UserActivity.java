package com.geobim.teamtask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.geobim.teamtask.R;
import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.entity.User;
import com.geobim.teamtask.thread.GetUserMsgThread;
import com.geobim.teamtask.thread.GetUserTokenThread;
import com.geobim.teamtask.thread.LoginThread;
import com.geobim.teamtask.thread.TimeoutThread;
import com.geobim.teamtask.ui.RecyclerListView.ExpandableAdapter;
import com.geobim.teamtask.ui.RecyclerListView.UserDetail;
import com.geobim.teamtask.util.NetworkUtils;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * 用户详细信息Activity
 * Created by Administrator on 2017/12/18.
 */

public class UserActivity extends BaseActivity implements OnClickListener {
    private ScrollView sv_layout;
    private RelativeLayout rl_layout, rl_title;
    private ImageButton ib_back, ib_back2, ib_refresh, ib_edit;
    private RecyclerView rvList;
    private ImageView iv_error;
    private TextView tv_title, tv_nonetwork, tv_realname, tv_jobposition;
    private boolean isConnect;
    private TimeoutThread timeoutThread;                      //超时判断线程
    private GetUserTokenThread getUserTokenThread;            //获取UserToken线程
    private GetUserMsgThread getUserMsgThread;                //获取User信息线程
    private ProgressWheel pg_wait;              //等待条
    private ExpandableAdapter adapter;
    @Override
    protected void initVariables() {
        //先检查网络状态
        isConnect = NetworkUtils.isAvailableByPing(UserActivity.this, false);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        StatusBarUtil.setTranslucent(UserActivity.this, 0);//状态栏半透明
        pg_wait = (ProgressWheel) findViewById(R.id.progress_user_wheel);
        sv_layout = (ScrollView) findViewById(R.id.sv_user_layout);
        ib_back = (ImageButton) findViewById(R.id.ib_user_back);
        ib_refresh = (ImageButton) findViewById(R.id.ib_user_refresh);
        ib_edit = (ImageButton) findViewById(R.id.ib_user_edit);
        rvList = (RecyclerView) findViewById(R.id.rv_user_recyclerlist);
        tv_realname = (TextView) findViewById(R.id.tv_user_realname);
        tv_jobposition = (TextView) findViewById(R.id.tv_user_jobposition);
        rl_title = (RelativeLayout) findViewById(R.id.rl_user_title);
        rl_layout = (RelativeLayout) findViewById(R.id.rl_user_layout);
        iv_error = (ImageView) findViewById(R.id.iv_user_error);
        tv_nonetwork = (TextView) findViewById(R.id.tv_user_nonetwork);
        ib_back2 = (ImageButton) findViewById(R.id.ib_user_back2);
        ib_back.setOnClickListener(this);
        ib_refresh.setOnClickListener(this);
        ib_edit.setOnClickListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        if (isConnect) {
            startTokenThread();
        } else {
            noNetWork();
        }
    }

    /**
     * 有网络的界面
     */
    private void haveNetWork(){
        sv_layout.setVisibility(View.VISIBLE);
        rl_title.setVisibility(View.GONE);
        rl_layout.setVisibility(View.GONE);
        iv_error.setVisibility(View.GONE);
        tv_nonetwork.setVisibility(View.GONE);
    }

    /**
     * 没有网络的界面
     */
    private void noNetWork() {
        sv_layout.setVisibility(View.GONE);
        rl_title.setVisibility(View.VISIBLE);
        rl_layout.setVisibility(View.VISIBLE);
        iv_error.setVisibility(View.VISIBLE);
        tv_nonetwork.setVisibility(View.VISIBLE);
        ib_back2.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_user_back:
            case R.id.ib_user_back2:
                finish();
                break;
            case R.id.ib_user_refresh:
                break;
            case R.id.ib_user_edit:
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //UserToken获取失败
                    getFailed();
                    break;
                case 104:
                    //解析错误
                    getError();
                    break;
                case 200:
                    //UserToken获取成功
                    startMsgThread();
                    break;
                case 300:
                    //Msg获取成功
                    getSuccess();
                    break;
            }
        }
    };

    /**
     * 获取UserToken
     */
    private void startTokenThread() {
        pg_wait.setVisibility(View.VISIBLE);
        if (timeoutThread == null) {
            timeoutThread = new TimeoutThread(handler);
            timeoutThread.start();//开启定时器线程
        }
        if (getUserTokenThread == null) {
            getUserTokenThread = new GetUserTokenThread(handler, User.getInstance().getTokenKey());
            getUserTokenThread.start();
        }
    }
    /**
     * 获取UserMsg
     */
    private void startMsgThread(){
        if (getUserMsgThread == null) {
            getUserMsgThread = new GetUserMsgThread(handler,User.getInstance().getUserToken(),User.getInstance().getId());
            getUserMsgThread.start();//开启定时器线程
        }
    }
    /**
     * 取消定时器
     */
    private void cancelThread() {
        pg_wait.setVisibility(View.GONE);
        if (timeoutThread != null) {
            timeoutThread.cancelTimer();
            timeoutThread.interrupt();
            timeoutThread = null;
        }
        if (getUserTokenThread != null) {
            getUserTokenThread.interrupt();
            getUserTokenThread = null;
        }
        if (getUserMsgThread != null) {
            getUserMsgThread.interrupt();
            getUserMsgThread = null;
        }
    }

    /**
     * 用户出错
     */
    private void getFailed() {
        cancelThread();
        noNetWork();
        SweetAlertDialog sad = new SweetAlertDialog(UserActivity.this);
        sad.setCancelable(false);
        sad.setCanceledOnTouchOutside(false);
        sad.setTitleText("用户信息发生变动，请重新登录");
        sad.setConfirmText("确定");
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 获取信息错误
     */
    private void getError() {
        cancelThread();
        noNetWork();
        tv_nonetwork.setText("解析错误，请重新刷新");
    }

    /**
     * 获取信息成功
     */
    private void getSuccess() {
        cancelThread();
        adapter = new ExpandableAdapter(this, UserDetail.getUserDetail());
        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                ExpandableAdapter expandableAdapter = (ExpandableAdapter) adapter;
                if (expandableAdapter.isExpand(groupPosition)) {
                    expandableAdapter.collapseGroup(groupPosition);
                } else {
                    expandableAdapter.expandGroup(groupPosition);
                }
            }
        });
        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {
                Toast.makeText(UserActivity.this, "子项：groupPosition = " + groupPosition + ", childPosition = " + childPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        rvList.setAdapter(adapter);
        haveNetWork();
    }

    /**
     * 请求超时
     */
    private void getTimeOut() {
        cancelThread();

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
            finish();
        }
        return false;
    }
}
