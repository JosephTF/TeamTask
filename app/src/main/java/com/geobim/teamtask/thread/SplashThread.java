package com.geobim.teamtask.thread;


import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.geobim.teamtask.activity.LoginActivity;

/**
 * 启动页线程：睡眠N秒后跳转到登录界面
 * @author Administrator
 *
 */
public class SplashThread extends Thread{
	private Activity mActivity;
	private int mSleepTime;
	public SplashThread(Activity activity,int sleeptime) {
		mActivity = activity;
		mSleepTime = sleeptime;
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
		Intent intent = new Intent(mActivity, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);  
		mActivity.startActivity(intent);
		mActivity.finish();
	}
}
