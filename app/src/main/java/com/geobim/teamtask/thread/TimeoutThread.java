package com.geobim.teamtask.thread;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
/**
 * 超时判断线程类
 * 倒计时15s，到0后发送超时message
 * @author Administrator
 *
 */
public class TimeoutThread extends Thread{
	private Handler mHandler;
	private int timeOutSet=15000;
	public TimeoutThread(Handler handler){
		mHandler=handler;
	}

	public Timer timer = new Timer();
	public TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			Message msgMessage = new Message();
			msgMessage.what = 1;
			mHandler.sendMessage(msgMessage);
		}
	};
	//继承Thread类，并改写其run方法
	@Override
	public void run(){
		super.run();
		timer.schedule(timerTask, timeOutSet);
	}

	/**
	 * 取消定时器
	 */
	public void cancelTimer(){
		if (timerTask != null){
			timerTask.cancel();  //将原任务从队列中移除

		}
		if (timer != null){
			timer.cancel();
		}
	}
}
