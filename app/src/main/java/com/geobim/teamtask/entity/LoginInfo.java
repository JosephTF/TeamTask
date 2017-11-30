package com.geobim.teamtask.entity;

import java.util.Date;

/**
 * 用户最近登录信息，最多保留100条数据
 * Created by Joseph on 2017/11/30.
 */

public class LoginInfo {
    private Date dateTime;      //最近登录时间
    private String IP;          //最近登录地址
    private String address;     //最近登录地址
    private String systemType;  //最近登录的客户端类型：Web，Pc，iOS，Android
    private String deviceId;    //最近登录的设备Id

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getiP() {
        return IP;
    }

    public void setiP(String IP) {
        this.IP = IP;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
