package com.geobim.teamtask.thread;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.geobim.teamtask.activity.LoginActivity;
import com.geobim.teamtask.activity.MainActivity;
import com.geobim.teamtask.util.LoginSaveUtil;

import java.util.Map;

/**
 * 启动页线程：睡眠N秒后跳转到登录界面
 *
 * @author Administrator
 */
public class SplashThread extends Thread {
    private Activity activity;
    private Handler handler;
    private Message msgMessage;
    private int sleepTime;
    private boolean isSaved;

    public SplashThread(Activity activity, Handler handler, int sleeptime) {
        this.activity = activity;
        this.handler =handler;
        this.sleepTime = sleeptime;
        msgMessage = new Message();
    }

    @Override
    public void run() {
        super.run();
        try {
            Map<String, String> map = new LoginSaveUtil(activity).getUserInfo("private.txt");
            isSaved = Boolean.parseBoolean(map.get("isSaved"));
        } catch (Exception e) {
        }
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "启动异常！", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (isSaved) {
            msgMessage.what = 100;
            handler.sendMessage(msgMessage);
        } else {
            msgMessage.what = 101;
            handler.sendMessage(msgMessage);
        }
    }
}
