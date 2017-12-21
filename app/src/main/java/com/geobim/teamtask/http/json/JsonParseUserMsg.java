package com.geobim.teamtask.http.json;

import android.util.Log;

import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解析获取用户信息
 * Created by Administrator on 2017/12/20.
 */

public class JsonParseUserMsg {
    private static final String TAG = "JsonParseUserMsg";

    /**
     * @param result 需要解析的数据
     * @throws JSONException 格式不对，转换异常
     */
    public static void parseUserMsgByOrgJson(String result) throws JSONException {

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
            User.getInstance().setId(userId.substring(3,userId.length()-1));
            User.getInstance().setIDCard(jsonObject.getString("IDCard"));
            User.getInstance().setEmail(jsonObject.getString("Email"));
            User.getInstance().setPhoneNumber(jsonObject.getString("PhoneNumber"));
            User.getInstance().setRealName(jsonObject.getString("RealName"));
            User.getInstance().setAvatar(jsonObject.getString("Avatar"));
            User.getInstance().setSex(jsonObject.getString("Sex"));
            User.getInstance().setNation(jsonObject.getString("Nation"));
            User.getInstance().setBirthday((jsonObject.getString("Birthday")).substring(0,10));
            User.getInstance().setMaritalStatus(jsonObject.getString("MaritalStatus"));
            User.getInstance().setNativePlace(jsonObject.getString("NativePlace"));
            User.getInstance().setLocation(jsonObject.getString("Location"));
            User.getInstance().setEducationLevel(jsonObject.getString("EducationLevel"));
            User.getInstance().setFirstWorkYear(jsonObject.getString("FirstWorkYear"));
            User.getInstance().setHealthCheckTime((jsonObject.getString("HealthCheckTime")).substring(0,10));
            User.getInstance().setBloodType(jsonObject.getString("BloodType"));
            User.getInstance().setRegisterDate((jsonObject.getString("RegisterDate")).substring(0,10));
//            String WorkHistory = jsonObj.getString("WorkHistory");
//            Log.i("AAAAAAAAAAAAAAAA",WorkHistory);
        }
    }
}
