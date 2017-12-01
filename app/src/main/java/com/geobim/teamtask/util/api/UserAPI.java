package com.geobim.teamtask.util.api;

/**
 * 用户管理API
 * 账号：13826510352
 * 密码：111111
 * Created by Joseph on 2017/11/30.
 */

public class UserAPI {
    /**
     * 获取地址：添加用户
     * @return Url
     */
    public static String getUserRegister(){
        return WebAPI.getUrl()+"/sys/user/register";
    }

    /**
     * 获取地址：批量导入用户
     * @return Url
     */
    public static String getUserImport(){
        return WebAPI.getUrl()+"/sys/user/import";
    }

    /**
     * 获取地址：判断用户是否存在
     * @return Url
     */
    public static String getUserExist(){
        return WebAPI.getUrl()+"/sys/user/exist";
    }

    /**
     * 获取地址：重置密码
     * @return Url
     */
    public static String getUserResetPassword(){
        return WebAPI.getUrl()+"/sys/user/reset_password";
    }

    /**
     * 获取地址：获取验证码
     * @return Url
     */
    public static String getUserVerifyCode(){
        return WebAPI.getUrl()+"/sys/user/verify_code";
    }

    /**
     * 获取地址：判断验证码是否有效
     * @return Url
     */
    public static String getUserVerifyCodeIsValid(){
        return WebAPI.getUrl()+"/sys/user/is_verify_code_valid";
    }

    /**
     * 获取地址：获取用户列表（筛选、分页、排序）
     * @return Url
     */
    public static String getUserList(){
        return WebAPI.getUrl()+"/sys/user/list";
    }

    /**
     * 获取地址：获取用户信息
     * @return Url
     */
    public static String getUserInfo(){
        return WebAPI.getUrl()+"/sys/user/info";
    }

    /**
     * 获取地址：更新用户信息
     * @return Url
     */
    public static String getUserUpdate(){
        return WebAPI.getUrl()+"/sys/user/update";
    }

    /**
     * 获取地址：获取用户头像
     * @return Url
     */
    public static String getUserAvatar(){
        return WebAPI.getUrl()+"/sys/user/avatar";
    }

    /**
     * 获取地址：更新用户头像
     * @return Url
     */
    public static String getUserAvatarUpdate(){
        return WebAPI.getUrl()+"/sys/user/update_avatar";
    }

    /**
     * 获取地址：登录
     * @return Url
     */
    public static String getUserLogin(){
        return WebAPI.getUrl()+"/sys/user/login";
    }

    /**
     * 获取地址：后台管理员登录
     * @return Url
     */
    public static String getUserLoginForAdmin(){
        return WebAPI.getUrl()+"/sys/user/login_for_admin";
    }

    /**
     * 获取地址：获取后台管理员列表（筛选、分页、排序）
     * @return Url
     */
    public static String getUserListForAdmin(){
        return WebAPI.getUrl()+"/sys/user/list_for_admin";
    }

    /**
     * 获取地址：获取应用的UserToken
     * @return Url
     */
    public static String getUserToken(){
        return WebAPI.getUrl()+"/sys/user/token";
    }

    /**
     * 获取地址：指定的密码是否与用户的旧密码一致
     * @return Url
     */
    public static String getUserPasswordIsSame(){
        return WebAPI.getUrl()+"/sys/user/is_same_password";
    }

    /**
     * 获取地址：获取当前用户在指定项目上具有权限的应用集合
     * @return Url
     */
    public static String getUserProjectApps(){
        return WebAPI.getUrl()+"/sys/user/project_apps";
    }

    /**
     * 获取地址：更新项目拥有者拥有的应用
     * @return Url
     */
    public static String getUserUpdateOwnerApps(){
        return WebAPI.getUrl()+"/sys/user/update_owner_apps";
    }
}
