package com.geobim.teamtask.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.geobim.teamtask.R;
import com.geobim.teamtask.entity.User;
import com.geobim.teamtask.thread.ResetPasswordThread;
import com.geobim.teamtask.thread.TimeoutThread;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * 修改密码界面
 * Created by Administrator on 2017/12/7.
 */

public class ResetPasswordActivity extends BaseActivity implements OnClickListener {
    private final String TAG = "ForgetActivity";
    private String newPassword, userToken,resetKey;
    private ImageButton ib_back;
    private EditText et_newpassword;
    private TextView tv_confirm;
    private ProgressWheel pg_wait;  //等待条
    private TimeoutThread timeoutThread;        //超时判断线程
    private ResetPasswordThread rptThread;
    private Handler handler;

    @Override
    protected void initVariables() {
        //变量初始化
        userToken = User.getInstance().getUserToken();
        resetKey="";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_resetpassword);
        StatusBarUtil.setTranslucent(ResetPasswordActivity.this, 0);//状态栏半透明
        ib_back = (ImageButton) findViewById(R.id.ib_reset_back);
        et_newpassword = (EditText) findViewById(R.id.et_reset_newpassword);
        tv_confirm = (TextView) findViewById(R.id.tv_reset_confirm);
        pg_wait = (ProgressWheel) findViewById(R.id.progress_reset_wheel);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        handler = new Handler() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 200) {
                    //修改成功
                    cancelThread();
                    resetSuccessDialog();
                } else if (msg.what == 202) {
                    //与旧密码相同
                    cancelThread();
                    passwordIsSameDialog();
                } else if (msg.what == 404) {
                    //密码更新失败
                    cancelThread();
                    resetErrorDialog();
                }else if(msg.what == 504){
                    //判断旧密码相同线程超时
                    cancelThread();
                    checkTimeOut();
                }
            }
        };
    }
    /*-------------------------------------  点击事件  -------------------------------------*/
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
                    startThread();
                }
                break;
        }
    }

    /*-------------------------------------  对话框  -------------------------------------*/
    /**
     * 改密失败
     */
    private void resetSuccessDialog(){
        SweetAlertDialog sad = new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
        sad.setTitleText("提示");
        sad.setContentText("密码修改成功！");
        sad.setConfirmText("确定");
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sad.show();
    }

    /**
     * 改密失败
     */
    private void resetErrorDialog(){
        SweetAlertDialog sad = new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
        sad.setTitleText("提示");
        sad.setContentText("抱歉，密码未能修改成功！");
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
     * 与旧密码相同
     */
    private void passwordIsSameDialog(){
        SweetAlertDialog sad = new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.WARNING_TYPE);
        sad.setTitleText("提示");
        sad.setContentText("不能与旧密码一致，请重新设置");
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
     * 请求超时
     */
    private void checkTimeOut() {
        SweetAlertDialog sad = new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
        sad.setTitleText("提示");
        sad.setContentText("请求超时，请稍后重试");
        sad.setConfirmText("确定");
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        sad.show();
    }

    /*-------------------------------------  线程  -------------------------------------*/
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
            rptThread = new ResetPasswordThread(handler, userToken, newPassword,resetKey);
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
            finish();
        }
        return false;
    }
}
