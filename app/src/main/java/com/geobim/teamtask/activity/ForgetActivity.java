package com.geobim.teamtask.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.geobim.teamtask.R;
import com.geobim.teamtask.util.ApkUtil;
import com.geobim.teamtask.util.RegexConstants;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;
import com.mob.MobSDK;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 忘记密码
 * Created by Administrator on 2017/12/4.
 */

public class ForgetActivity extends BaseActivity implements OnClickListener, OnLongClickListener {
    private final String TAG = "ForgetActivity";
    private int TIME = 60;//倒计时60s这里应该多设置些因为mob后台需要60s
    private boolean isCountdown, isRepeat;//验证码是否在倒计时中
    private static final int CODE_REPEAT = 0; //重新发送
    private String countryNumber, countryName;
    private ImageButton ib_back;
    private ImageView iv_country;
    private TextView tv_countrynum, tv_getVerificationCode, tv_confirm;
    private EditText et_phonenumber, et_code;
    private TimerTask tt;
    private Timer tm;
    private Handler handler;

    @Override
    protected void initVariables() {
        //默认中国区号
        countryNumber = getString(R.string.register_countrynum);
        countryName = "中国";
        // 通过代码注册你的AppKey和AppSecret
        MobSDK.init(ForgetActivity.this, ApkUtil.getMobAppKey(), ApkUtil.getMobAppSecret());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget);
        StatusBarUtil.setTranslucent(ForgetActivity.this, 0);//状态栏半透明
        ib_back = findViewById(R.id.ib_forget_return);
        iv_country = findViewById(R.id.iv_forget_countrynum);
        tv_countrynum = findViewById(R.id.tv_forget_countrynum);
        tv_getVerificationCode = findViewById(R.id.tv_forget_getVerificationCode);
        tv_confirm = findViewById(R.id.tv_forget_confirm);
        et_phonenumber = findViewById(R.id.et_forget_phonenumber);
        et_code = findViewById(R.id.et_forget_code);
        ib_back.setOnClickListener(this);
        iv_country.setOnClickListener(this);
        tv_countrynum.setOnClickListener(this);
        tv_getVerificationCode.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        iv_country.setOnLongClickListener(this);
        tv_countrynum.setOnLongClickListener(this);
    }

    @Override
    protected void loadData() {
        handler = new Handler() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == CODE_REPEAT) {
                    tv_getVerificationCode.setEnabled(true);
                    tv_getVerificationCode.setText(R.string.register_getVerificationCode_again);
                    tm.cancel();//取消任务
                    tt.cancel();//取消任务
                    TIME = 60;//时间重置
                    isRepeat = true;
                } else {
                    isCountdown = true;
                    tv_getVerificationCode.setEnabled(false);
                    tv_getVerificationCode.setText(Html.fromHtml(getString(R.string.register_receive_msg, TIME)));
                }
            }
        };

        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_forget_return:
                Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_forget_countrynum:
            case R.id.tv_forget_countrynum:
                Intent intent_country = new Intent(ForgetActivity.this, CountryActivity.class);
                startActivityForResult(intent_country, 10001);
                break;
            case R.id.tv_forget_getVerificationCode:
                String phonenum = et_phonenumber.getText().toString();
                //第一次判断：输入框是否为空
                if (phonenum.isEmpty()) {
                    Toast.makeText(ForgetActivity.this, R.string.forget_enternum, Toast.LENGTH_SHORT).show();
                } else {
                    //第二次判断：是否是中国区域号码
                    if ("+86".equals(countryNumber)) {
                        //第三次判断：是否符合手机号码正则表达式
                        if (phonenum.matches(RegexConstants.REGEX_MOBILE_EXACT)) {
                            verificationCodeDialog(phonenum);//发送验证码弹框
                        } else {
                            Toast.makeText(ForgetActivity.this, R.string.register_phonenumber, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (countryNumber.length() > 7) {
                            verificationCodeDialog(phonenum);//发送验证码弹框
                        } else {
                            Toast.makeText(ForgetActivity.this, R.string.register_phonenumber, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                break;
            case R.id.tv_forget_confirm:
                String verificationCode = et_code.getText().toString();
                if(verificationCode.isEmpty()){
                    Toast.makeText(ForgetActivity.this, R.string.register_enterVerificationCode, Toast.LENGTH_SHORT).show();
                }else{
                    //校验验证码
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.iv_forget_countrynum:
            case R.id.tv_forget_countrynum:
                Toast.makeText(ForgetActivity.this, "国家：" + countryName + "  区号：" + countryNumber, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    /**
     * 发送验证码确认框
     */
    private void verificationCodeDialog(String phonenum) {
        final String phone = countryNumber + phonenum;
        SweetAlertDialog sad = new SweetAlertDialog(ForgetActivity.this);
        sad.setTitleText(getString(R.string.register_getVerificationCode));
        sad.setContentText(getString(R.string.register_make_sure_mobile_detail) + phone);
        sad.setCancelText(getString(R.string.common_cancel));
        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        sad.setConfirmText(getString(R.string.common_confirm));
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                // 重新获取验证码短信
                //做倒计时操作
                SMSSDK.getVerificationCode(countryNumber, phone.trim(), null);
                timeTask();
            }
        });
        sad.show();
    }

    /**
     * 倒计时线程
     */
    private void timeTask() {
        tm = new Timer();
        tt = new TimerTask() {
            @Override
            public void run() {
                if (TIME != 0) {
                    handler.sendEmptyMessage(TIME--);
                } else {
                    Message msg = new Message();
                    msg.what = CODE_REPEAT;
                    handler.sendMessage(msg);
                }
            }
        };
        tm.schedule(tt, 0, 1000);
    }

    /**
     * 国家列表回调方法，从第二个页面回来的时候会执行这个方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10001:
                    countryName = data.getStringExtra("countryName");
                    countryNumber = data.getStringExtra("countryNumber");
                    tv_countrynum.setText(countryNumber);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (isCountdown) {
            outState.putBoolean("isCoutdown", isCountdown);
            outState.putInt("RemainingTime", TIME);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean("isCoutdown")) {
            TIME = savedInstanceState.getInt("RemainingTime");
            timeTask();
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
            Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
