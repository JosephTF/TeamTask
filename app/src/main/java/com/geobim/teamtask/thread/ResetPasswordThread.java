package com.geobim.teamtask.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.geobim.teamtask.http.HttpUrlGet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 修改密码
 * Created by Administrator on 2017/12/9.
 */

public class ResetPasswordThread extends Thread {
    private final String TAG = "ResetPasswordThread";
    private Handler handler;
    private String userToken,passsword;
    private Message msgMessage;
    private HttpUrlGet httpUtils;

    public ResetPasswordThread(Handler handler, String userToken,String passsword) {
        this.handler = handler;
        this.userToken = userToken;
        this.passsword = passsword;
        msgMessage = new Message();
        httpUtils = new HttpUrlGet();
    }

    @Override
    public void run() {
        super.run();
        //判断密码是否与旧密码一致
        String pwdIsSame = httpUtils.getUserPasswordIsSameUrl(userToken,passsword);
        try {
            final String result = httpUtils.getResult(pwdIsSame);
            try {
                JSONObject json = new JSONObject(result);
                int Code = json.getInt("Code");
                switch (Code) {
                    case 200:
                        boolean isSame = json.getBoolean("Data");
                        if(!isSame){
                            //与旧密码不一样，设置新密码
                            msgMessage.what = 200;
                            handler.sendMessage(msgMessage);
                        }else{
                            //与旧密码一样，重新设置
                            msgMessage.what = 404;
                            handler.sendMessage(msgMessage);
                        }
                        break;
                    default:
                        Log.i(TAG,"用户是否存在解析失败");
                        break;
                }
            } catch (JSONException e) {
                //用户是否存在解析失败
                Log.i(TAG,"用户是否存在解析失败");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
