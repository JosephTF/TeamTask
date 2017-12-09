package com.geobim.teamtask.http.json;


import android.util.Log;

import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.entity.User;
import com.mongodb.BasicDBObject;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonParseUser {
    private static final String TAG = "JsonParseUser";

    /**
     * @param result 需要解析的数据
     * @throws JSONException 格式不对，转换异常
     */
    public static void parseUserByOrgJson(String result) throws JSONException {

        // 解析并保存回执信息
        JSONObject jsonObj = new JSONObject(result);
        ApiReturnInfo.getInstance().setOK(jsonObj.getBoolean("IsOk"));
        ApiReturnInfo.getInstance().setCode(jsonObj.getInt("Code"));
        ApiReturnInfo.getInstance().setMessage(jsonObj.getString("Message"));
        ApiReturnInfo.getInstance().setData(jsonObj.getString("Data"));

        // 获取返回信息
        String data = ApiReturnInfo.getInstance().getData();
        if (data != "null") {
            JSONObject jsonObject = new JSONObject(data);
            String userId = jsonObject.getString("_id");
            User.getInstance().setId(new ObjectId(userId.substring(3,userId.length()-1)));
            User.getInstance().setRealName(jsonObject.getString("RealName"));
            User.getInstance().setPhoneNumber(jsonObject.getString("PhoneNumber"));
            User.getInstance().setEmail(jsonObject.getString("Email"));
            User.getInstance().setAvatar(jsonObject.getString("Avatar"));
            User.getInstance().setTokenKey(jsonObject.getString("TokenKey"));
        }
    }
}