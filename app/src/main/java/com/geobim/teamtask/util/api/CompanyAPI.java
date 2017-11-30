package com.geobim.teamtask.util.api;

/**
 * 单位管理API
 * Created by Joseph on 2017/11/30.
 */

public class CompanyAPI {
    /**
     * 获取地址：获取单位类型
     * @return Url
     */
    public static String getCompanyTypes(){
        return WebAPI.getUrl()+"/sys/company/types";
    }

    /**
     * 获取地址：获取单位列表（筛选、分页、排序）
     * @return Url
     */
    public static String getCompanyList(){
        return WebAPI.getUrl()+"/sys/company/list";
    }

    /**
     * 获取地址：获取单位信息
     * @return Url
     */
    public static String getCompanyInfo(){
        return WebAPI.getUrl()+"/sys/company/info";
    }

    /**
     * 获取地址：创建单位
     * @return Url
     */
    public static String getCompanyCreate(){
        return WebAPI.getUrl()+"/sys/company/create";
    }

    /**
     * 获取地址：批量导入
     * @return Url
     */
    public static String getCompanyImport(){
        return WebAPI.getUrl()+"/sys/company/import";
    }

    /**
     * 获取地址：修改单位信息
     * @return Url
     */
    public static String getCompanyUpdate(){
        return WebAPI.getUrl()+"/sys/company/update";
    }

    /**
     * 获取地址：删除单位（批量）
     * @return Url
     */
    public static String getCompanyDelete(){
        return WebAPI.getUrl()+"/sys/company/delete";
    }
}
