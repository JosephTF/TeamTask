package com.geobim.teamtask.thread;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
/**
 * ��ʱ�ж��߳���
 * ����ʱ15s����0���ͳ�ʱmessage
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
    //�̳�Thread�࣬����д��run����        
	@Override
    public void run(){  
		super.run();
	    timer.schedule(timerTask, timeOutSet);
    }  
	
	/**
	 * ȡ����ʱ��
	 */
	public void cancelTimer(){
		if (timerTask != null){
			timerTask.cancel();  //��ԭ����Ӷ������Ƴ�
	 
		}
		if (timer != null){
			timer.cancel();
		}
	}
}
