package com.geobim.teamtask.thread;


import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.geobim.teamtask.activity.LoginActivity;
import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.http.HttpUrlGet;
import com.geobim.teamtask.http.json.JsonParseUser;
import com.geobim.teamtask.util.api.UserAPI;
import com.geobim.teamtask.util.api.WebAPI;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginThread extends Thread{
	private final String TAG = "LoginThread";
	private Handler handler;
	private String username,password;
	private Message msgMessage;
	public LoginThread(Handler handler, String username, String password) {
		this.handler = handler;
		this.username = username;
		this.password = password;
		msgMessage = new Message();
	}

	@Override
	public void run() {
		super.run();
		//传入登录名和密码,获取登录地址
		HttpUrlGet httpUtils = new HttpUrlGet();
		String loginUrl = httpUtils.getUserLoginUrl(username, password);
		try {
			final String result = httpUtils.getResult(loginUrl);
			try {
				JsonParseUser.parseUserByOrgJson(result);
				switch (ApiReturnInfo.getInstance().getCode()){
					case 200:
						msgMessage.what = 200;
						handler.sendMessage(msgMessage);
						break;
					case 0:
						msgMessage.what = 0;
						handler.sendMessage(msgMessage);
						break;
				}
			} catch (JSONException e) {
				//用户信息解析失败
				msgMessage.what = 104;
				handler.sendMessage(msgMessage);
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}