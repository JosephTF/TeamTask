package com.geobim.teamtask.entity;

import java.util.Date;

/**
 * 单位信息
 * Created by Joseph on 2017/11/30.
 */

public class CompanyInfo {
    private String companyName;             //所属单位
    private String companyType;             //单位类型
    private String Role;                    //角色
    private boolean isBuilder;              //是否是施工人员
    private boolean isCompanyAdmin;         //是否是单位管理员
    private String subcontractor;           //所在分包单位
    private String lineBidSection;          //所属线路标段
    private String station;                 //所属站点
    private String constructionMajor;       //施工专业
    private String team;                    //班组
    private String job;                     //岗位
    private Date approachTime;              //进场时间
    private Date exitTime;                  //退场时间

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public boolean isBuilder() {
        return isBuilder;
    }

    public void setBuilder(boolean builder) {
        isBuilder = builder;
    }

    public boolean isCompanyAdmin() {
        return isCompanyAdmin;
    }

    public void setCompanyAdmin(boolean companyAdmin) {
        isCompanyAdmin = companyAdmin;
    }

    public String getSubcontractor() {
        return subcontractor;
    }

    public void setSubcontractor(String subcontractor) {
        this.subcontractor = subcontractor;
    }

    public String getLineBidSection() {
        return lineBidSection;
    }

    public void setLineBidSection(String lineBidSection) {
        this.lineBidSection = lineBidSection;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getConstructionMajor() {
        return constructionMajor;
    }

    public void setConstructionMajor(String constructionMajor) {
        this.constructionMajor = constructionMajor;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Date getApproachTime() {
        return approachTime;
    }

    public void setApproachTime(Date approachTime) {
        this.approachTime = approachTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }
}
