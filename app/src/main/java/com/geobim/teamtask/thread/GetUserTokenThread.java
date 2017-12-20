package com.geobim.teamtask.thread;

import android.os.Handler;
import android.os.Message;

import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.http.HttpUrlGet;
import com.geobim.teamtask.http.json.JsonParseUserToken;

import org.json.JSONException;

import java.io.IOException;

/**
 * 获取应用的UserToken
 * Created by Administrator on 2017/12/20.
 */

public class GetUserTokenThread extends Thread{
    private final String TAG = "GetUserTokenThread";
    private Handler handler;
    private String tokenkey;
    private Message msgMessage;
    public GetUserTokenThread(Handler handler, String tokenkey) {
        this.handler = handler;
        this.tokenkey = tokenkey;
        msgMessage = new Message();
    }

    @Override
    public void run() {
        super.run();
        //传入登录名和密码,获取登录地址
        HttpUrlGet httpUtils = new HttpUrlGet();
        String userTokenUrl = httpUtils.getUserTokenUrl(tokenkey);
        try {
            final String result = httpUtils.getResult(userTokenUrl);
            try {
                JsonParseUserToken.parseUserTokenByOrgJson(result);
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
