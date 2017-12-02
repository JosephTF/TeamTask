package com.geobim.teamtask.entity;

/**
 * Api返回信息
 * Created by Joseph on 2017/12/2.
 */

public class ApiReturnInfo {
    private volatile static ApiReturnInfo apiReturnInfo;
    private ApiReturnInfo(){}
    public static ApiReturnInfo getInstance(){
        if(apiReturnInfo==null){
            synchronized (ApiReturnInfo.class){
                if(apiReturnInfo==null){
                    apiReturnInfo = new ApiReturnInfo();
                }
            }
        }
        return  apiReturnInfo;
    }

    private boolean isOK;
    private int Code;
    private String Message;
    private String Data;

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
