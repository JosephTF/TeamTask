package com.geobim.teamtask.util.api;

/**
 * API地址和签名
 * Created by Joseph on 2017/11/30.
 */

public class WebAPI {
    /**
     * 获取服务器地址
     * @return url
     */
    public final static String getUrl(){
        return "http://app.bstar5.com:43210/api/v1";
    }

    /**
     * 获取签名Key值
     * @return Key值
     */
    public final static String getKey(){
        return "3260D276-A08A-40EC-BDE0-E0D0A7BAFF15";
    }
}

