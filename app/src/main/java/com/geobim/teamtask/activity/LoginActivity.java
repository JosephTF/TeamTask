package com.geobim.teamtask.activity;

import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geobim.teamtask.R;
import com.geobim.teamtask.thread.LoginThread;
import com.geobim.teamtask.thread.TimeoutThread;
import com.geobim.teamtask.ui.SmoothCheckBox.SmoothCheckBox;
import com.geobim.teamtask.ui.TitanicTextView.Titanic;
import com.geobim.teamtask.ui.TitanicTextView.TitanicTextView;
import com.geobim.teamtask.util.*;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends BaseActivity implements OnClickListener, OnFocusChangeListener, OnTouchListener {
    private static final String TAG = "LoginActivity";
    private String username, password;
    private RelativeLayout rl_login, rl_login_savepassword;
    private EditText et_username, et_password;
    private ImageView iv_username, iv_password;
    private TextView tv_login, tv_forget, tv_register;
    private SmoothCheckBox cb_save;            //记住密码CheckBox
    private LoginSaveUtil loginService;        //用户密码保存
    private PopupWindow mPopupWindow;        //搭载Loading
    private Titanic titanic;                //Loading动画
    private TitanicTextView ttv_loading;    //Loading控件
    private TimeoutThread timeoutThread;    //超时判断线程
    private LoginThread loginThread;        //登录验证线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucent(LoginActivity.this, 0);
        rl_login = findViewById(R.id.rl_login);
        rl_login_savepassword = findViewById(R.id.rl_login_savepassword);
        et_username = findViewById(R.id.et_login_username);
        et_password = findViewById(R.id.et_login_password);
        iv_username = findViewById(R.id.iv_login_username);
        iv_password = findViewById(R.id.iv_login_password);
        tv_login = findViewById(R.id.login_start);
        tv_forget = findViewById(R.id.login_forget);
        tv_register = findViewById(R.id.login_register);
        cb_save = findViewById(R.id.cb_login_savelogin);
        tv_login.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        rl_login_savepassword.setOnClickListener(this);
        rl_login.setOnTouchListener(this);
        et_username.setOnFocusChangeListener(this);
        et_password.setOnFocusChangeListener(this);
        ViewGroup vg = null;
        View popupView = getLayoutInflater().inflate(R.layout.popup_loading, vg);
        mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        ttv_loading = popupView.findViewById(R.id.ttv_login_loading);
    }

    @Override
    protected void loadData() {
        loginService = new LoginSaveUtil(this);
        titanic = new Titanic();
        try {
            Map<String, String> map = loginService.getUserInfo("private.txt");
            et_username.setText(map.get("username"));
            et_password.setText(map.get("password"));
            if (map.get("password").length() > 0) {
                cb_save.setChecked(true);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_start:
                //点击登录后
                //先检查网络状态
                boolean isConnect = NetworkUtils.isAvailableByPing();
                Log.i(TAG, "isConnect:" + isConnect);
                if (isConnect) {
                    //网络已连接
                    username = et_username.getText().toString().trim();
                    password = et_password.getText().toString();
                    if (TextUtils.isEmpty(username)) {
                        et_username.requestFocus();
                        animLoginBtn();
                        Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(password)) {
                        et_password.requestFocus();
                        animLoginBtn();
                        Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    } else if (password.length() < 4) {
                        et_password.requestFocus();
                        animLoginBtn();
                        Toast.makeText(LoginActivity.this, "密码长度不能小于4位！", Toast.LENGTH_SHORT).show();
                    } else {
                        showPopupLoading(true);//验证信息的时候显示ProgressBar等待图标，验证成功直接切Activity不用隐藏，验证不成功再隐藏等待用户再次验证
                        login();
                    }
                }
                break;
            case R.id.login_forget:
                Toast.makeText(this, "请联系管理员获取密码", Toast.LENGTH_LONG).show();
                break;
            case R.id.rl_login_savepassword:
                cb_save.performClick();
                break;
            case R.id.login_register:
                Toast.makeText(LoginActivity.this, "暂不启动", Toast.LENGTH_SHORT).show();
                this.finish();
            default:
                break;
        }
    }

    /**
     * 焦点监听
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_login_username:
                if (hasFocus) {
                    iv_username.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.login_usericon_focus));
                } else {
                    iv_username.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.login_usericon));
                }
                break;
            case R.id.et_login_password:
                if (hasFocus) {
                    iv_password.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.login_password_focus));
                } else {
                    iv_password.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.login_password));
                }
                break;
            default:
                break;
        }
    }

    /**
     * TouchListener
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        switch (v.getId()) {
            case R.id.rl_login:
                rl_login.setFocusable(true);
                rl_login.setFocusableInTouchMode(true);
                rl_login.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(rl_login.getWindowToken(), 0);
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 用户登录
     */
    private void login() {
        if (timeoutThread == null) {
            timeoutThread = new TimeoutThread(handler);
            timeoutThread.start();//开启定时器线程
        }
        if (loginThread == null) {
            loginThread = new LoginThread(this, username, password);
            loginThread.start();
        }
    }

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

    /**
     * 登录成功，保存密码，跳转界面
     */
    public void loginSuccess() {
        cancelThread();
        if (cb_save.isChecked()) {
            //保存密码
            try {
                boolean result = loginService.saveToRom(password, username);
                if (result) {
                    Log.i(TAG, "用户密码保存成功");
                } else {
                    Log.i(TAG, "用户密码保存失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
        } else {
            try {
                boolean result = loginService.saveToRom("", username);
                if (result) {
                    Log.i(TAG, "用户保存成功");
                } else {
                    Log.i(TAG, "用户保存失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
        }
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 登录超时，取消线程
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                showPopupLoading(false);
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("提示")
                        .setMessage("登录超时，请稍后重试")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelThread();
                            }
                        })
                        .create().show();
            }
        }

        ;
    };

    /**
     * 控制缓冲条显隐
     */
    private void showPopupLoading(boolean show) {
        if (show) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            titanic.start(ttv_loading);
        } else {
            titanic.cancel();
            mPopupWindow.dismiss();
        }
    }

    /**
     * 控件动画
     */
    private void animLoginBtn() {
        TranslateAnimation down = new TranslateAnimation(-60, 0, 0, 0);//位移动画，从button的上方300像素位置开始
        down.getFillBefore();
        down.setFillAfter(true);
        down.setInterpolator(new BounceInterpolator());//弹跳动画,要其它效果的当然也可以设置为其它的值
        down.setDuration(600);//持续时间
        tv_login.startAnimation(down);//设置按钮运行该动画效果
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SweetAlertDialog sd = new SweetAlertDialog(this);
            sd.setCancelable(true);
            sd.setCanceledOnTouchOutside(true);
            sd.show();
        }
        return false;
    }
}