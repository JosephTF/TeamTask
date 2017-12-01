package com.geobim.teamtask.http;

import okhttp3.OkHttpClient;

import com.geobim.teamtask.util.MD5;

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
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String getResult(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    /**
     * 获取登录地址
     * MD5加密后要加"&isPasswordEncoded=true"标记
     *
     * @param username
     * @param password
     * @return
     */
    public String getLoginUrl(String url, String username, String password) {
        return url + "?userName=" + username + "&password=" + MD5.md5(password).toUpperCase(Locale.US) + "&isPasswordEncoded=true" + "&deviceType=Andriod" + "&entryId=MS";
    }


}
