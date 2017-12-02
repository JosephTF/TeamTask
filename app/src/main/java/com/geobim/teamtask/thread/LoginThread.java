package com.geobim.teamtask.thread;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.geobim.teamtask.activity.LoginActivity;
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
	private LoginActivity mActivity;
	private String username,password;
	public LoginThread(LoginActivity activity,String username,String password) {
		mActivity = activity;
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		super.run();
		try {
			sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//传入登录名和密码,获取登录地址
    	HttpUrlGet httpUtils = new HttpUrlGet();
		//转换为JSON
		String loginUrl = httpUtils.getLoginUrl(username, password);
		try {
			final String result = httpUtils.getResult(loginUrl);
			Log.i("TAG","result:"+result);
			try {
				JsonParseUser.parseUserByOrgJson(result);
			} catch (JSONException e) {
				Log.i(TAG, "用户信息解析失败");
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		//更新UI,在UI线程中
//		mActivity.runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				if("developer".equals(user)&&"1234".equals(pw)){
//					mActivity.loginSuccess();
//				}else{
//					new AlertDialog.Builder(mActivity)
//							.setTitle("提示")
//							.setMessage("用户名或密码错误")
//							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//								}
//							})
//							.create().show();
//				}
//                    else if("用户名或密码错误".equals(EMOSUser.getInstance().getDetailMessage())){
//                    	pg_login.setVisibility(View.GONE);
//                    	cancelThread();
//                    	new AlertDialog.Builder(LoginActivity.this)
// 					   		.setTitle("提示")
// 					   		.setMessage("用户名或密码错误")
// 					   		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
// 					   			@Override
// 					   			public void onClick(DialogInterface dialog, int which) {
// 					       		}
// 					   		})
// 					   		.create().show();
//                    }else{
//                    	pg_login.setVisibility(View.GONE);
//                    	cancelThread();
//                    	new AlertDialog.Builder(LoginActivity.this)
// 					   		.setTitle("提示")
// 					   		.setMessage("服务器连接失败，请稍后重试")
// 					   		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
// 					   			@Override
// 					   			public void onClick(DialogInterface dialog, int which) {
// 					       		}
// 					   		})
// 					   		.create().show();
//                    }
			}
//		});
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//	}
}