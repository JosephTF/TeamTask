package com.geobim.teamtask.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.geobim.teamtask.R;
import com.geobim.teamtask.activity.ARActivity;
import com.geobim.teamtask.activity.AboutActivity;
import com.geobim.teamtask.activity.DraftBoxActicity;
import com.geobim.teamtask.activity.FeedBackActivity;
import com.geobim.teamtask.activity.LoginActivity;
import com.geobim.teamtask.activity.MainActivity;
import com.geobim.teamtask.activity.OCRActivity;
import com.geobim.teamtask.activity.SecurityActivity;
import com.geobim.teamtask.activity.SplashActivity;
import com.geobim.teamtask.activity.UserActivity;
import com.geobim.teamtask.activity.VersionActivity;
import com.geobim.teamtask.base.ui.BaseFragment;
import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.entity.User;
import com.geobim.teamtask.thread.LoginThread;
import com.geobim.teamtask.thread.TimeoutThread;
import com.geobim.teamtask.util.ActivityList;
import com.geobim.teamtask.util.LoginSaveUtil;
import com.geobim.teamtask.util.NetworkUtils;

import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import butterknife.ButterKnife;

/**
 * 个人中心界面
 */
public class FragmentMe extends BaseFragment implements OnClickListener {
    private String username, password, realname, phonenum, email;
    private View view;
    private RelativeLayout rl_userdetail, rl_ar, rl_ocr, rl_security, rl_feedback, rl_draftbox, rl_version, rl_about, rl_exit;
    private TextView tv_realname, tv_phonenum, tv_email;
    private TimeoutThread timeoutThread;        //超时判断线程
    private LoginThread loginThread;            //登录验证线程

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initView();
    }

    private void initVariables() {
        try {
            Map<String, String> map = new LoginSaveUtil(getContext()).getUserInfo("private.txt");
            username = map.get("username");
            password = map.get("password");
            realname = map.get("realname");
            phonenum = map.get("phonenum");
            email = map.get("email");
        } catch (Exception e) {
        }
        //先检查网络状态
        boolean isConnect = NetworkUtils.isAvailableByPing(getContext(), true);
        if (isConnect) {
            if (timeoutThread == null) {
                timeoutThread = new TimeoutThread(handler);
                timeoutThread.start();//开启定时器线程
            }
            if (loginThread == null) {
                loginThread = new LoginThread(handler, username, password);
                loginThread.start();
            }
        }
    }

    private void initView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_me, null);
        ButterKnife.bind(this, view);
        mContentView.addView(view);
        rl_userdetail = view.findViewById(R.id.rl_me_userdetail);
        rl_ar = view.findViewById(R.id.rl_me_ar);
        rl_ocr = view.findViewById(R.id.rl_me_ocr);
        rl_security = view.findViewById(R.id.rl_me_security);
        rl_feedback = view.findViewById(R.id.rl_me_feedback);
        rl_draftbox = view.findViewById(R.id.rl_me_draftbox);
        rl_version = view.findViewById(R.id.rl_me_version);
        rl_about = view.findViewById(R.id.rl_me_about);
        rl_exit = view.findViewById(R.id.rl_me_exit);
        tv_realname = view.findViewById(R.id.tv_me_realname);
        tv_phonenum = view.findViewById(R.id.tv_me_phonenum);
        tv_email = view.findViewById(R.id.tv_me_email);
        tv_realname.setText(realname);
        tv_phonenum.setText(phonenum);
        tv_email.setText(email);
        rl_userdetail.setOnClickListener(this);
        rl_ar.setOnClickListener(this);
        rl_ocr.setOnClickListener(this);
        rl_security.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_draftbox.setOnClickListener(this);
        rl_version.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_me_userdetail:
                Intent intent2user = new Intent(getContext(), UserActivity.class);
                getContext().startActivity(intent2user);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.rl_me_ar:
                Intent intent2ar = new Intent(getContext(), ARActivity.class);
                getContext().startActivity(intent2ar);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.rl_me_ocr:
                Intent intent2ocr = new Intent(getContext(), OCRActivity.class);
                getContext().startActivity(intent2ocr);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.rl_me_security:
                Intent intent2security = new Intent(getContext(), SecurityActivity.class);
                getContext().startActivity(intent2security);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.rl_me_feedback:
                Intent intent2feedback = new Intent(getContext(), FeedBackActivity.class);
                getContext().startActivity(intent2feedback);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.rl_me_draftbox:
                Intent intent2draftbox = new Intent(getContext(), DraftBoxActicity.class);
                getContext().startActivity(intent2draftbox);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.rl_me_version:
                Intent intent2version = new Intent(getContext(), VersionActivity.class);
                getContext().startActivity(intent2version);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.rl_me_about:
                Intent intent2about = new Intent(getContext(), AboutActivity.class);
                getContext().startActivity(intent2about);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.rl_me_exit:
                SweetAlertDialog sad = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                sad.setTitleText("确认退出系统");
                sad.setContentText(ApiReturnInfo.getInstance().getMessage());
                sad.setConfirmText("确定");
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
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //登录失败
                    cancelThread();
                    SweetAlertDialog sad = new SweetAlertDialog(getContext());
                    sad.setCancelable(false);
                    sad.setCanceledOnTouchOutside(false);
                    sad.setTitleText("用户信息发生变动，请重新登录");
                    sad.setConfirmText("确定");
                    sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                    break;
                case 104:
                case 504:
                    //登录错误、超时
                    cancelThread();
                    break;
                case 200:
                    //登录成功
                    cancelThread();
                    tv_realname.setText(User.getInstance().getRealName());
                    tv_phonenum.setText(User.getInstance().getPhoneNumber());
                    tv_email.setText(User.getInstance().getEmail());
                    break;
            }
        }
    };

    /**
     * 取消定时器
     */
    private void cancelThread() {
        if (timeoutThread != null) {
            timeoutThread.cancelTimer();
            timeoutThread.interrupt();
            timeoutThread = null;
        }
        if (loginThread != null) {
            loginThread.interrupt();
            loginThread = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
