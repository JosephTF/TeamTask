package com.geobim.teamtask.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.geobim.teamtask.http.HttpUrlGet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/9.
 */

public class ResetPasswordThread extends Thread {
    private final String TAG = "ResetPasswordThread";
    private Handler handler;
    private String userToken,passsword;

    public ResetPasswordThread(Handler handler, String userToken,String passsword) {
        this.handler = handler;
        this.userToken = userToken;
        this.passsword = passsword;
    }

    @Override
    public void run() {
        super.run();
        Message msgMessage = new Message();
        //传入令牌和密码
        HttpUrlGet httpUtils = new HttpUrlGet();
        //判断密码是否与旧密码一致
        String pwdIsSame = httpUtils.getUserPasswordIsSameUrl(userToken,passsword);
        try {
            final String result = httpUtils.getResult(pwdIsSame);
            try {
                JSONObject json = new JSONObject(result);
                int Code = json.getInt("Code");
                switch (Code) {
                    case 200:
                        boolean isExist = json.getBoolean("Data");
                        if(isExist){
                            msgMessage.what = 200;
                            handler.sendMessage(msgMessage);
                        }else{
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
