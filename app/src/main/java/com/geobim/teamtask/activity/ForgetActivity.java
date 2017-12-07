package com.geobim.teamtask.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
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
import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.http.HttpUrlGet;
import com.geobim.teamtask.http.json.JsonParseUser;
import com.geobim.teamtask.thread.CheckUserExistThread;
import com.geobim.teamtask.thread.LoginThread;
import com.geobim.teamtask.thread.TimeoutThread;
import com.geobim.teamtask.util.ApkUtil;
import com.geobim.teamtask.util.RegexConstants;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;
import com.mob.MobSDK;
import com.mob.tools.utils.ResHelper;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * 忘记密码
 * Created by Administrator on 2017/12/4.
 */

public class ForgetActivity extends BaseActivity implements OnClickListener, OnLongClickListener {
    private final String TAG = "ForgetActivity";
    private int TIME = 60;//倒计时60s这里应该多设置些因为mob后台需要60s
    private boolean isCountdown, isRepeat;//验证码是否在倒计时中
    private static final int CODE_REPEAT = 0; //重新发送
    private String countryNumber, countryName, phonenum, countryNumber_confirm, phonenum_confirm;
    private ImageButton ib_back;
    private ImageView iv_country;
    private TextView tv_countrynum, tv_getVerificationCode, tv_confirm;
    private EditText et_phonenumber, et_code;
    private TimeoutThread timeoutThread;        //超时判断线程
    private CheckUserExistThread cueThread;     //用户存在判断线程
    private TimerTask tt;
    private Timer tm;
    private Handler handler;
    private EventHandler eh;
    private ProgressWheel pg_wait;  //等待条

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
        pg_wait = findViewById(R.id.progress_forget_wheel);
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
                    tv_getVerificationCode.setText(R.string.register_getVerificationCode_again);
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

        eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        afterSubmit(result, data);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        //afterGet(result, data);
                    } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                        //获取语音版验证码成功后的执行动作
                        afterGetVoice(result, data);
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
                phonenum = et_phonenumber.getText().toString().trim();
                //第一次判断：输入框是否为空
                if (phonenum.isEmpty()) {
                    Toast.makeText(ForgetActivity.this, R.string.forget_enternum, Toast.LENGTH_SHORT).show();
                } else {
                    //第二次判断：是否是中国区域号码
                    if ("+86".equals(countryNumber)) {
                        //第三次判断：是否符合手机号码正则表达式
                        if (phonenum.matches(RegexConstants.REGEX_MOBILE_EXACT)) {
                            //第四次判断：用户是否存在
                            startThread();
                        } else {
                            Toast.makeText(ForgetActivity.this, R.string.register_phonenumber, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (countryNumber.length() > 7) {
                            startThread();
                        } else {
                            Toast.makeText(ForgetActivity.this, R.string.register_phonenumber, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                break;
            case R.id.tv_forget_confirm:
                pg_wait.setVisibility(View.VISIBLE);
                String verificationCode = et_code.getText().toString().trim();
                if (verificationCode.isEmpty()) {
                    Toast.makeText(ForgetActivity.this, R.string.register_enterVerificationCode, Toast.LENGTH_SHORT).show();
                } else {
                    //校验验证码
                    SMSSDK.submitVerificationCode(countryNumber_confirm, phonenum_confirm, verificationCode);
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
     * 用户不存在，错误弹框
     */
    private void userIsNotExist() {
        SweetAlertDialog sad = new SweetAlertDialog(ForgetActivity.this, SweetAlertDialog.ERROR_TYPE);
        sad.setTitleText(phonenum);
        sad.setContentText(getString(R.string.forget_usernotexist));
        sad.setConfirmText(getString(R.string.common_confirm));
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        sad.show();
    }

    /**
     * 发送验证码确认框
     */
    private void verificationCodeDialog(final String phonenum) {
        if (!isRepeat) {
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
                    //确定区号和手机号
                    countryNumber_confirm = countryNumber;
                    phonenum_confirm = phonenum;
                    // 重新获取验证码短信
                    SMSSDK.getVerificationCode(countryNumber_confirm, phonenum_confirm);
                    //做倒计时操作
                    timeTask();
                }
            });
            sad.show();
        } else {
            SweetAlertDialog sad = new SweetAlertDialog(ForgetActivity.this);
            sad.setTitleText(getString(R.string.forget_getVerificationCode_again));
            sad.setContentText(getString(R.string.forget_getVerificationCode_method));
            sad.setCancelText(getString(R.string.forget_getVerificationCode_voice));
            sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    //弹出语音播报警告提示
                    getVoiceSweetDialog();
                }
            });
            sad.setConfirmText(getString(R.string.forget_getVerificationCode_sms));
            sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    //确定区号和手机号
                    countryNumber_confirm = countryNumber;
                    phonenum_confirm = phonenum;
                    // 重新获取验证码短信
                    SMSSDK.getVerificationCode(countryNumber, phonenum.trim());
                    //做倒计时操作
                    timeTask();
                }
            });
            sad.show();
        }
    }

    /**
     * 语音播报提示
     */
    private void getVoiceSweetDialog() {
        SweetAlertDialog sad = new SweetAlertDialog(ForgetActivity.this, SweetAlertDialog.WARNING_TYPE);
        sad.setTitleText(getString(R.string.forget_getVerificationCode_voice));
        sad.setContentText(getString(R.string.forget_sendsounds));
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
                //确定区号和手机号
                countryNumber_confirm = countryNumber;
                phonenum_confirm = phonenum;
                // 重新获取语音报播
                SMSSDK.getVoiceVerifyCode(countryNumber, phonenum.trim());
                //做倒计时操作
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
                if (TIME > 1) {
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

    /**
     * 提交验证码成功后的执行事件
     *
     * @param result
     * @param data
     */
    protected void afterSubmit(final int result, final Object data) {
        Log.i(TAG, "验证成功");
        runOnUiThread(new Runnable() {
            public void run() {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //验证成功
                    handler.sendEmptyMessage(CODE_REPEAT);
                    pg_wait.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(ForgetActivity.this, ResetPasswordActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ((Throwable) data).printStackTrace();
                    // 验证码不正确
                    String message = ((Throwable) data).getMessage();
                    int resId = 0;
                    try {
                        JSONObject json = new JSONObject(message);
                        int status = json.getInt("status");
                        resId = ResHelper.getStringRes(ForgetActivity.this, "smssdk_error_detail_" + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (resId == 0) {
                        pg_wait.setVisibility(View.INVISIBLE);
                        Toast.makeText(ForgetActivity.this, R.string.forget_wrong, Toast.LENGTH_SHORT).show();
                    }
                    if (resId > 0) {
                        pg_wait.setVisibility(View.INVISIBLE);
                        Toast.makeText(ForgetActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 获取语音验证码成功后,的执行动作
     *
     * @param result
     * @param data
     */
    protected void afterGetVoice(final int result, final Object data) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    int resId = ResHelper.getStringRes(ForgetActivity.this, "smssdk_send_sounds_success");
                    if (resId > 0) {
                        Toast.makeText(ForgetActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    // 根据服务器返回的网络错误，给toast提示
                    int status = 0;
                    try {
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");
                        status = object.optInt("status");
                        if (!TextUtils.isEmpty(des)) {
                            Toast.makeText(ForgetActivity.this, des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        SMSLog.getInstance().w(e);
                    }
                    //  如果木有找到资源，默认提示
                    int resId = 0;
                    if (status >= 400) {
                        resId = ResHelper.getStringRes(ForgetActivity.this, "smssdk_error_desc_" + status);
                    } else {
                        resId = ResHelper.getStringRes(ForgetActivity.this, "smssdk_network_error");
                    }
                    if (resId > 0) {
                        Toast.makeText(ForgetActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isCoutdown", isCountdown);
        outState.putBoolean("isRepeat", isRepeat);
        outState.putInt("RemainingTime", TIME);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isRepeat = savedInstanceState.getBoolean("isRepeat");
        isCountdown = savedInstanceState.getBoolean("isCoutdown");
        if (isCountdown) {
            //正在倒计时
            TIME = savedInstanceState.getInt("RemainingTime");
            timeTask();
        } else {
            //没有倒计时
            if (isRepeat) {
                tv_getVerificationCode.setText(R.string.register_getVerificationCode_again);
            }
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
        if (cueThread == null) {
            cueThread = new CheckUserExistThread(handler, phonenum);
            cueThread.start();
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
        if (cueThread != null) {
            cueThread.interrupt();
            cueThread = null;
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

    /**
     * 销毁短信注册
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销回调接口registerEventHandler必须和unregisterEventHandler配套使用，否则可能造成内存泄漏。
        SMSSDK.unregisterEventHandler(eh);
    }
}
