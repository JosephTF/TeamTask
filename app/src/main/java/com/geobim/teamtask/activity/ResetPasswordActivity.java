package com.geobim.teamtask.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.geobim.teamtask.R;
import com.geobim.teamtask.thread.CheckUserExistThread;
import com.geobim.teamtask.thread.ResetPasswordThread;
import com.geobim.teamtask.thread.TimeoutThread;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by Administrator on 2017/12/7.
 */

public class ResetPasswordActivity extends BaseActivity implements OnClickListener {
    private final String TAG = "ForgetActivity";
    private String newPassword;
    private ImageButton ib_back;
    private EditText et_newpassword;
    private TextView tv_confirm;
    private ProgressWheel pg_wait;  //等待条
    private TimeoutThread timeoutThread;        //超时判断线程
    private ResetPasswordThread rptThread;
    private Handler handler;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_resetpassword);
        StatusBarUtil.setTranslucent(ResetPasswordActivity.this, 0);//状态栏半透明
        ib_back = findViewById(R.id.ib_reset_back);
        et_newpassword = findViewById(R.id.et_reset_newpassword);
        tv_confirm = findViewById(R.id.tv_reset_confirm);
        pg_wait = findViewById(R.id.progress_reset_wheel);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        handler = new Handler() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 200) {
                    verificationCodeDialog(phonenum);//用户存在，发送验证码弹框
                    cancelThread();
                } else if (msg.what == 404) {
                    userIsNotExist();//用户不存在
                    cancelThread();
                } else if (msg.what == CODE_REPEAT) {
                    isCountdown = false;//倒计时结束
                    isRepeat = true;//非首次发送验证码
                    tv_getVerificationCode.setEnabled(true);
                    et_phonenumber.setEnabled(true);
                    tv_getVerificationCode.setText(R.string.forget_getVerificationCode_again);
                    tm.cancel();//取消任务
                    tt.cancel();//取消任务
                    TIME = 60;//时间重置
                } else {
                    isCountdown = true;
                    tv_getVerificationCode.setEnabled(false);
                    et_phonenumber.setEnabled(false);
                    tv_getVerificationCode.setText(Html.fromHtml(getString(R.string.forget_receive_msg, TIME)));
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_reset_back:
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_reset_confirm:
                newPassword = et_newpassword.getText().toString();
                if (newPassword.length() < 6 || newPassword.length() > 16) {
                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.reset_illegal), Toast.LENGTH_SHORT).show();
                } else {

                }
                break;
        }
    }

    /**
     * 开始线程
     */
    private void startThread() {
        pg_wait.setVisibility(View.VISIBLE);
        if (timeoutThread == null) {
            timeoutThread = new TimeoutThread(handler);
            timeoutThread.start();//开启定时器线程
        }
        if (rptThread == null) {
            rptThread = new CheckUserExistThread(handler, newPassword);
            rptThread.start();
        }
    }

    /**
     * 取消线程
     */
    private void cancelThread() {
        pg_wait.setVisibility(View.GONE);
        if (timeoutThread != null) {
            timeoutThread.cancelTimer();
            timeoutThread.interrupt();
            timeoutThread = null;
        }
        if (rptThread != null) {
            rptThread.interrupt();
            rptThread = null;
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
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
