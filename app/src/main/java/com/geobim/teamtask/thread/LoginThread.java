package com.geobim.teamtask.thread;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.geobim.teamtask.activity.LoginActivity;

public class LoginThread extends Thread{
	private String user,pw;
	private LoginActivity mActivity;
	public LoginThread(LoginActivity activity,String username,String password) {
		mActivity = activity;
		user = username;
		pw = password;
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
//    	String url = httpUtils.getLoginUrl(username, password);
//        try {
//            final String loginResult = httpUtils.getResult(url);
//
//            try {
//				JsonParseUser.parseUserByOrgJson(loginResult);
//			} catch (JSONException e) {
//				Log.i(TAG, "用户信息解析失败");
//				e.printStackTrace();
//			}
		//更新UI,在UI线程中
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if("developer".equals(user)&&"1234".equals(pw)){
					mActivity.loginSuccess();
				}else{
					new AlertDialog.Builder(mActivity)
							.setTitle("提示")
							.setMessage("用户名或密码错误")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								}
							})
							.create().show();
				}
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
		});
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
	}
}