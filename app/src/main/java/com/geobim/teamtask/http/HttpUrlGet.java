package com.geobim.teamtask.http;

import okhttp3.OkHttpClient;

import com.geobim.teamtask.util.MD5;
import com.geobim.teamtask.util.api.UserAPI;
import com.geobim.teamtask.util.api.WebAPI;

import java.io.IOException;
import java.util.Locale;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用户登录
 *
 * @author Administrator
 */
public class HttpUrlGet {
    private OkHttpClient client = new OkHttpClient();

    public String getResult(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    /**
     * 获取地址：判断用户是否存在
     */
    public String getUserExistUrl(String username){
        return UserAPI.getUserExist() +"?appkey=" + WebAPI.getKey() + "&userName=" + username;
    }

    /**
     * 获取登录地址
     */
    public String getUserLoginUrl(String username, String password) {
        return UserAPI.getUserLogin() + "?appkey=" + WebAPI.getKey() + "&userName=" + username + "&password=" + password;
    }
}
