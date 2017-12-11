package com.geobim.teamtask.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.http.HttpUrlGet;
import com.geobim.teamtask.http.json.JsonParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 判断用户是否存在线程
 * Created by Administrator on 2017/12/7.
 */

public class CheckUserExistThread extends Thread{
    private final String TAG = "CheckUserExistThread";
    private Handler handler;
    private String username;
    public CheckUserExistThread(Handler handler, String username) {
        this.handler = handler;
        this.username = username;
    }

    @Override
    public void run() {
        super.run();
        Message msgMessage = new Message();
        //传入登录手机号
        HttpUrlGet httpUtils = new HttpUrlGet();
        String userExistUrl = httpUtils.getUserExistUrl(username);
        try {
            final String result = httpUtils.getResult(userExistUrl);
            try {
                JSONObject json = new JSONObject(result);
                int Code = json.getInt("Code");
                switch (Code) {
                    case 200:
                        boolean isExist = json.getBoolean("Data");
                        if(isExist){
                            //用户存在
                            msgMessage.what = 200;
                            handler.sendMessage(msgMessage);
                        }else{
                            //用户不存在
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