package com.geobim.teamtask.thread;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.geobim.teamtask.activity.LoginActivity;
import com.geobim.teamtask.http.HttpUrlGet;
import com.geobim.teamtask.util.api.UserAPI;
import com.geobim.teamtask.util.api.WebAPI;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginThread extends Thread{
	private LoginActivity mActivity;
	private Map<String,String> map;
	private String username,password;
	public LoginThread(LoginActivity activity,String username,String password) {
		mActivity = activity;
		this.username = username;
		this.password = password;
		map=new HashMap<String, String>();
		map.put("AppKey", WebAPI.getKey());
		map.put("UserName",username);
		map.put("Password",password);
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
//    	HttpUrlGet httpUtils = new HttpUrlGet();
//		//转换为JSON
//		String user = httpUtils.bolwingJson(username, password);
//		try {
//			final String result = httpUtils.getResult(UserAPI.getUserLogin(), user);
//			Log.i("TAG","result:"+result);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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