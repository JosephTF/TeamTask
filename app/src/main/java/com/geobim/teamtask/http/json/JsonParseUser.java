package com.geobim.teamtask.http.json;


import android.util.Log;

import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.entity.User;

import org.json.JSONException;
import org.json.JSONObject;


public class JsonParseUser {
    private static final String TAG = "JsonParseUser";

    /**
     * @throws JSONException 格式不对，转换异常
     * @param    result            需要解析的数据
     */
    public static void parseUserByOrgJson(String result) throws JSONException {
        // 使用该方法解析思路，遇到大括号用JsonObject，中括号用JsonArray
        // 第一个是大括号{}
        JSONObject jsonObj = new JSONObject(result);
        // 解析并保存
        ApiReturnInfo.getInstance().setOK(jsonObj.getBoolean("IsOk"));
        ApiReturnInfo.getInstance().setCode(jsonObj.getInt("Code"));
        ApiReturnInfo.getInstance().setMessage(jsonObj.getString("Message"));
        ApiReturnInfo.getInstance().setData(jsonObj.getString("Data"));
        String data = ApiReturnInfo.getInstance().getData();
        if (data != "null") {
            JSONObject jsonObject = new JSONObject(data);
            String userId = jsonObject.getString("_id");
            User.getInstance().setID(userId.substring(3, userId.length() - 1));
            Log.i(TAG, User.getInstance().getID());
            User.getInstance().setRealName(jsonObject.getString("RealName"));
            User.getInstance().setPhoneNumber(jsonObject.getString("PhoneNumber"));
            User.getInstance().setEmail(jsonObject.getString("Email"));
            User.getInstance().setAvatar(jsonObject.getString("Avatar"));
            User.getInstance().setTokenKey(jsonObject.getString("TokenKey"));
        }
    }
}