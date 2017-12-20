package com.geobim.teamtask.http;

import com.geobim.teamtask.util.api.UserAPI;
import com.geobim.teamtask.util.api.WebAPI;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;

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
     * 获取地址：添加用户
     * Data:用户信息
     * 格式：
     * {
     * "IDCard": "string", //身份证号，*必填
     * "RealName": "string", //用户姓名，*必填
     * "Password": "string", //用户密码，*必填
     * "Email": "string", //用户邮箱
     * "PhoneNumber": "string", //用户手机号
     * "JobPosition": "string", //工作职位
     * "Sex": 0, //性别：0-未透露；1-男性；2-女性
     * "Location": "string", //所在地
     * "QQ": "string", //qq号
     * }
     */
    public String getUserRegisterUrl(JSONArray data) {
        return UserAPI.getUserRegister() + "?appkey=" + WebAPI.getKey() + "&Data=" + data;
    }

    /**
     * 获取地址：批量导入用户
     * UserToken:用户令牌（包含用户Id及访问权限等信息）
     * File:用户数据压缩包（仅支持.rar、.zip格式）
     */
    public String getUserImportUrl(String userToken, File file) {
        return UserAPI.getUserImport() + "?userToken=" + userToken + "&File=" + file;
    }

    /**
     * 获取地址：判断用户是否存在
     */
    public String getUserExistUrl(String username) {
        return UserAPI.getUserExist() + "?appkey=" + WebAPI.getKey() + "&userName=" + username;
    }

    /**
     * 获取地址：重置密码
     * ResetKey：重置密码的密钥，通过邮件或手机发送
     */
    public String getUserResetPasswordUrl(String resetKey, String password) {
        return UserAPI.getUserResetPassword() + "?resetKey=" + resetKey + "&password=" + password;
    }

    /**
     * 获取地址：获取验证码
     * UserName:手机号或邮箱
     * CodeType:验证码类型，可用值为：Register ChangePassword ForgotPassword
     */
    public String getUserVerifyCodeUrl(String userName, String codeType) {
        return UserAPI.getUserVerifyCode() + "?appkey=" + WebAPI.getKey() + "&userName=" + userName + "&codeType=" + codeType;
    }

    /**
     * 获取地址：判断验证码是否有效
     * UserName:手机号或邮箱
     * ResetKey：收到的验证码
     * CodeType:验证码类型，可用值为：Register ChangePassword ForgotPassword
     */
    public String getUserVerifyCodeIsValidUrl(String userName, String resetKey, String codeType) {
        return UserAPI.getUserVerifyCodeIsValid() + "?appkey=" + WebAPI.getKey() + "&userName=" + userName + "&resetKey=" + resetKey + "&codeType=" + codeType;
    }

    /**
     * 获取地址：获取用户列表（筛选、分页、排序）
     * UserToken:用户令牌（包含用户Id及访问权限等信息）
     * ListParams:集合的筛选、分页、排序参数（非必填）
     * ListParams格式：
     * {
     * "Search": "[Expression]", //筛选表达式，默认为空，表示不筛选
     * "Page": { //分页，默认为空，表示不分页
     * "Index": 0, //分页索引，*必填
     * "Count": 0 //分页数量，*必填
     * },
     * "Sort": [{ //排序，默认为空数组，表示不排序
     * "Property": "PropertyName", //第一个排序的属性名，*必填
     * "Ascending": false //是否升序排序，如果为false，则降序排列，默认为true
     * }, {
     * "...": ""
     * }],
     * "Map": ["PropertyName1", "..."] //返回的属性映射数组,默认为空数组，表示返回所有属性
     * }
     * PS:如果PageIndex和PageCount都为0，则表示不分页；如果搜索表达式为空，则表示不搜索；Sort表示按指定的属性升序或降序排列。
     */
    public String getUserListUrl(String userToken) {
        return UserAPI.getUserList() + "?userToken=" + userToken;
    }

    public String getUserListUrl(String userToken, JSONArray listParams) {
        return UserAPI.getUserList() + "?userToken=" + userToken + "&listParams=" + listParams;
    }

    /**
     * 获取地址：获取用户信息
     * UserToken:用户令牌（包含用户Id及访问权限等信息）
     * Id:用户Id
     */
    public String getUserInfoUrl(String userToken, String id) {
        return UserAPI.getUserInfo() + "?userToken=" + userToken + "&id=" + id;
    }

    /**
     * 获取地址：更新用户信息
     * UserToken:用户令牌（包含用户Id及访问权限等信息）
     * Data:用户信息
     * Data格式：
     * {
     * "_id": "ObjectId", //用户Id，*必填
     * "...": "" //需要更新的数据
     * }
     */
    public String getUserUpdateUrl(String userToken, JSONArray data) {
        return UserAPI.getUserUpdate() + "?userToken=" + userToken + "&data=" + data;
    }

    /**
     * 获取地址：获取用户头像
     */
    public String getUserAvatarUrl(String username) {
        return UserAPI.getUserAvatar() + "?appkey=" + WebAPI.getKey() + "&username=" + username;
    }

    /**
     * 获取地址：更新用户头像
     */
    public String getUserAvatarUpdateUrl(String userToken, File file) {
        return UserAPI.getUserAvatarUpdate() + "?userToken=" + userToken + "&file=" + file;
    }

    /**
     * 获取地址：登录
     * Data:额外的登录信息
     * Data格式：
     * {
     * "DeviceId": "string", //设备Id,即设备的唯一标识
     * "...": "...", //其它信息
     * }
     */
    public String getUserLoginUrl(String username, String password) {
        return UserAPI.getUserLogin() + "?appkey=" + WebAPI.getKey() + "&userName=" + username + "&password=" + password;
    }

    public String getUserLoginUrl(String username, String password, JSONArray data) {
        return UserAPI.getUserLogin() + "?appkey=" + WebAPI.getKey() + "&userName=" + username + "&password=" + password + "&data:" + data;
    }

    /**
     * 获取地址：后台管理员登录
     * Data:额外的登录信息
     * Data格式：
     * {
     * "DeviceId": "string", //设备Id,即设备的唯一标识
     * "...": "...", //其它信息
     * }
     */
    public String getUserLoginForAdminUrl(String username, String password) {
        return UserAPI.getUserLoginForAdmin() + "?appkey=" + WebAPI.getKey() + "&username=" + username + "&password=" + password;
    }

    public String getUserLoginForAdminUrl(String username, String password, JSONArray data) {
        return UserAPI.getUserLoginForAdmin() + "?appkey=" + WebAPI.getKey() + "&username=" + username + "&password=" + password + "&data:" + data;
    }

    /**
     * 获取地址：获取后台管理员列表（筛选、分页、排序）
     * ListParams格式为：
     * {
     * "Search": "[Expression]", //筛选表达式，默认为空，表示不筛选
     * "Page": { //分页，默认为空，表示不分页
     * "Index": 0, //分页索引，*必填
     * "Count": 0 //分页数量，*必填
     * },
     * "Sort": [{ //排序，默认为空数组，表示不排序
     * "Property": "PropertyName", //第一个排序的属性名，*必填
     * "Ascending": false //是否升序排序，如果为false，则降序排列，默认为true
     * }, {
     * "...": ""
     * }],
     * "Map": ["PropertyName1", "..."] //返回的属性映射数组,默认为空数组，表示返回所有属性
     * }
     * PS:如果PageIndex和PageCount都为0，则表示不分页；如果搜索表达式为空，则表示不搜索；
     * Sort表示按指定的属性升序或降序排列。
     * ==如果不是超级管理员，则获取到的为登录用户所在单位下的用户。==
     */
    public String getUserListForAdminUrl(String userToken, JSONArray listparams) {
        return UserAPI.getUserListForAdmin() + "?userToken=" + userToken + "&listparams=" + listparams;
    }

    /**
     * 获取地址：获取应用的UserToken
     * TokenKey:用户登录成功后能获取到该值
     */
    public String getUserTokenUrl(String tokenkey) {
        return UserAPI.getUserToken() + "?appkey=" + WebAPI.getKey() + "&tokenkey=" + tokenkey;
    }

    /**
     * 获取地址：指定的密码是否与用户的旧密码一致
     * UserToken:用户令牌（包含用户Id及访问权限等信息）
     * Password:新密码
     */
    public String getUserPasswordIsSameUrl(String userToken, String password) {
        return UserAPI.getUserPasswordIsSame() + "?usertoken=" + userToken + "&password=" + password;
    }

    /**
     * 获取地址：获取当前用户在指定项目上具有权限的应用集合
     */
    public String getUserProjectAppsUrl(String userToken, String id, int platform) {
        return UserAPI.getUserProjectApps() + "?userToken=" + userToken + "&id=" + id + "&platform=" + platform;
    }

    /**
     * 获取地址：更新项目拥有者拥有的应用
     */
    public String getUserUpdateOwnerAppsUrl() {
        return UserAPI.getUserUpdateOwnerApps();
    }
}