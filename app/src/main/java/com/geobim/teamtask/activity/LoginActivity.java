package com.geobim.teamtask.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.geobim.teamtask.R;
import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.entity.User;
import com.geobim.teamtask.thread.LoginThread;
import com.geobim.teamtask.thread.TimeoutThread;
import com.geobim.teamtask.ui.SmoothCheckBox.SmoothCheckBox;
import com.geobim.teamtask.util.LoginSaveUtil;
import com.geobim.teamtask.util.NetworkUtils;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.Map;


public class LoginActivity extends BaseActivity implements OnClickListener, OnFocusChangeListener, OnTouchListener {
    private final String TAG = "LoginActivity";
    private String username, password;
    private RelativeLayout rl_login;
    private EditText et_username, et_password;
    private ImageView iv_username, iv_password;
    private TextView tv_logo_title, tv_login, tv_forget, tv_register;
    private LoginSaveUtil loginService;         //用户密码保存
    private TimeoutThread timeoutThread;        //超时判断线程
    private LoginThread loginThread;            //登录验证线程
    private Typeface tf;                        //字体
    private ProgressWheel pg_wait;              //等待条

    @Override
    protected void initVariables() {
        tf = Typeface.createFromAsset(getAssets(), "fonts/ArvoRegular.ttf");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucent(LoginActivity.this, 0);//状态栏半透明
        rl_login = findViewById(R.id.rl_login);
        et_username = findViewById(R.id.et_login_username);
        et_password = findViewById(R.id.et_login_password);
        iv_username = findViewById(R.id.iv_login_username);
        iv_password = findViewById(R.id.iv_login_password);
        tv_logo_title = findViewById(R.id.login_logo_title);
        tv_login = findViewById(R.id.login_start);
        tv_forget = findViewById(R.id.login_forget);
        tv_register = findViewById(R.id.login_register);
        pg_wait = findViewById(R.id.progress_login_wheel);
        tv_logo_title.setTypeface(tf);
        tv_login.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        rl_login.setOnTouchListener(this);
        et_username.setOnFocusChangeListener(this);
        et_password.setOnFocusChangeListener(this);
    }

    @Override
    protected void loadData() {
        loginService = new LoginSaveUtil(this);
        try {
            Map<String, String> map = loginService.getUserInfo("private.txt");
            et_username.setText(map.get("username"));
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_start:
                //点击登录后
                //先检查网络状态
                boolean isConnect = NetworkUtils.isAvailableByPing(LoginActivity.this);
                Log.i(TAG, "isConnect:" + isConnect);
                if (isConnect) {
                    /**
                     * 网络连接状态
                     */
                    username = et_username.getText().toString().trim();
                    password = et_password.getText().toString();
                    if (TextUtils.isEmpty(username)) {
                        et_username.requestFocus();
                        animLoginBtn();
                        Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(password)) {
                        et_password.requestFocus();
                        animLoginBtn();
                        Toast.makeText(LoginActivity.this, "密码  不能为空！", Toast.LENGTH_SHORT).show();
                    } else if (password.length() < 4) {
                        et_password.requestFocus();
                        animLoginBtn();
                        Toast.makeText(LoginActivity.this, "密码长度不能小于4位！", Toast.LENGTH_SHORT).show();
                    } else {
                        login();
                    }
                }
                break;
            case R.id.login_forget:
                Toast.makeText(this, "功能研发中…", Toast.LENGTH_LONG).show();
                break;
            case R.id.login_register:
                Toast.makeText(LoginActivity.this, "暂时只开放内部用户使用，敬请期待新版本！", Toast.LENGTH_SHORT).show();
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
        pg_wait.setVisibility(View.VISIBLE);
        if (timeoutThread == null) {
            timeoutThread = new TimeoutThread(handler);
            timeoutThread.start();//开启定时器线程
        }
        if (loginThread == null) {
            loginThread = new LoginThread(handler, username, password);
            loginThread.start();
        }
    }

    /**
     * 取消定时器
     */
    private void cancelLogin() {
        pg_wait.setVisibility(View.GONE);
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

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    loginFailed();
                    break;
                case 104:
                    loginError();
                    break;
                case 200:
                    loginSuccess();
                    break;
                case 504:
                    loginTimeOut();
                    break;
            }
        }
    };

    /**
     * 登录失败
     */
    private void loginFailed() {
        cancelLogin();
        SweetAlertDialog sad = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
        sad.setTitleText("登录失败");
        sad.setContentText(ApiReturnInfo.getInstance().getMessage());
        sad.setConfirmText("确定");
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        sad.show();
    }

    /**
     * 登录错误
     */
    private void loginError() {
        cancelLogin();
        SweetAlertDialog sad = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
        sad.setTitleText("提示");
        sad.setContentText("登录错误！");
        sad.setConfirmText("确定");
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        sad.show();
    }

    /**
     * 登录成功，保存密码，跳转界面
     */
    private void loginSuccess() {
        //保存密码
        try {
            boolean result = loginService.saveToRom(password, username, User.getInstance().getTokenKey());
            if (result) {
                Log.i(TAG, "用户密码保存成功");
            } else {
                Log.i(TAG, "用户密码保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 请求超时
     */
    private void loginTimeOut() {
        cancelLogin();
        SweetAlertDialog sad = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
        sad.setTitleText("提示");
        sad.setContentText("登录超时，请稍后重试");
        sad.setConfirmText("确定");
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        sad.show();
    }

    /**
     * 登录按钮动画
     * 效果：左右晃动
     */
    private void animLoginBtn() {
        TranslateAnimation down = new TranslateAnimation(-60, 0, 0, 0);//位移动画，从button的上方300像素位置开始
        down.getFillBefore();
        down.setFillAfter(true);
        down.setInterpolator(new BounceInterpolator());//弹跳动画,要其它效果的当然也可以设置为其它的值
        down.setDuration(600);//持续时间
        tv_login.startAnimation(down);//设置按钮运行该动画效果
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
                    finish();
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