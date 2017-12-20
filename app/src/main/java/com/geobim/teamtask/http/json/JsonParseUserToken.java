package com.geobim.teamtask.http.json;

import com.geobim.teamtask.entity.ApiReturnInfo;
import com.geobim.teamtask.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解析获取应用的UserToken
 * Created by Administrator on 2017/12/20.
 */

public class JsonParseUserToken {
    private static final String TAG = "JsonParseUserToken";

    /**
     * @param result 需要解析的数据
     * @throws JSONException 格式不对，转换异常
     */
    public static void parseUserTokenByOrgJson(String result) throws JSONException {

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
            User.getInstance().setUserToken(jsonObject.getString("UserToken"));
        }
    }
}
