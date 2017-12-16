package com.geobim.teamtask.thread;


import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.geobim.teamtask.activity.LoginActivity;
import com.geobim.teamtask.activity.MainActivity;
import com.geobim.teamtask.ui.FragmentView.demo.TabLayoutDemoTwo;
import com.geobim.teamtask.util.LoginSaveUtil;

import java.util.Map;

/**
 * 启动页线程：睡眠N秒后跳转到登录界面
 *
 * @author Administrator
 */
public class SplashThread extends Thread {
    private Activity mActivity;
    private int mSleepTime;
    private boolean isSaved;
    private LoginSaveUtil loginService;         //用户密码保存

    public SplashThread(Activity activity, int sleeptime) {
        mActivity = activity;
        mSleepTime = sleeptime;
        loginService = new LoginSaveUtil(mActivity);
        try {
            Map<String, String> map = loginService.getUserInfo("private.txt");
            isSaved = Boolean.parseBoolean(map.get("isSaved"));
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(mSleepTime);
        } catch (InterruptedException e) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity, "启动异常！", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(isSaved){
            Intent intent = new Intent(mActivity, TabLayoutDemoTwo.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            mActivity.startActivity(intent);
        }else {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            mActivity.startActivity(intent);
        }
    }
}
