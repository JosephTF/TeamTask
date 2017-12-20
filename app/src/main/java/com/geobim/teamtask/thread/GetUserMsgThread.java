package com.geobim.teamtask.thread;

import android.os.Handler;
import android.os.Message;

import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.http.HttpUrlGet;
import com.geobim.teamtask.http.json.JsonParseUserMsg;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/20.
 */

public class GetUserMsgThread extends Thread {
    private final String TAG = "GetUserMsgThread";
    private Handler handler;
    private String usertoken, id;
    private Message msgMessage;

    public GetUserMsgThread(Handler handler, String usertoken, String id) {
        this.handler = handler;
        this.usertoken = usertoken;
        this.id = id;
        msgMessage = new Message();
    }

    @Override
    public void run() {
        super.run();
        //传入登录名和密码,获取登录地址
        HttpUrlGet httpUtils = new HttpUrlGet();
        String userInfoUrl = httpUtils.getUserInfoUrl(usertoken, id);
        try {
            final String result = httpUtils.getResult(userInfoUrl);
            try {
                JsonParseUserMsg.parseUserMsgByOrgJson(result);
                switch (ApiReturnInfo.getInstance().getCode()) {
                    case 200:
                        msgMessage.what = 300;
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
