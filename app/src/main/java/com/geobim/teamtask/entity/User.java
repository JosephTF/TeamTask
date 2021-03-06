package com.geobim.teamtask.entity;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * 用户信息
 * Created by Josph on 2017/11/30.
 */

public class User {
    private volatile static User user;

    private User() {
    }

    public static User getInstance() {
        if (user == null) {
            synchronized (User.class) {
                if (user == null) {
                    user = new User();
                }
            }
        }
        return user;
    }

    private String Id;                              //Id
    private String IDCard;                          //身份证
    private String email;                           //邮箱，可作为用户名登录
    private String phoneNumber;                     //手机号，可作为用户名登录
    private String realName;                        //用户姓名
    private String avatar;                          //用户头像文件Id
    private String sex;                                //性别：0-未透露；1-男性；2-女性
    private String nation;                          //民族
    private String birthday;                        //出生日期
    private String maritalStatus;                   //婚姻状况
    private String nativePlace;                     //籍贯
    private String location;                        //户口所在地
    private String educationLevel;                  //教育程度
    private String firstWorkYear;                      //参加工作年份
    private String healthCheckTime;                 //体检时间
    private String bloodType;                       //血型
    private String registerDate;                    //用户注册时间
    private ArrayList<LoginInfo> loginInfo;         //用户最近登录信息，最多保留100条数据
    private WorkHistory workHistory;                //最近一次工作经历
    private FamilyInfo familyInfo;                  //家属信息
    private PeaceCard peaceCard;                    //正式平安卡号
    private PeaceAgentCard peaceAgentCard;          //平安卡代理卡
    private CompanyInfo companyInfo;                //单位信息
    private WorkInfo workInfo;                      //工种信息
    private TrainingInfo trainingInfo;              //培训信息
    private Attachments attachments;                //附件信息
    private String TokenKey;                        //用户安全Key
    private String UserToken;                       //用户令牌（包含用户Id及访问权限等信息）

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        if("1"==sex){
            return "男";
        }else if("2"==sex){
            return "女";
        }else{
            return "保密";
        }
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getFirstWorkYear() {
        return firstWorkYear;
    }

    public void setFirstWorkYear(String firstWorkYear) {
        this.firstWorkYear = firstWorkYear;
    }

    public String getHealthCheckTime() {
        return healthCheckTime;
    }

    public void setHealthCheckTime(String healthCheckTime) {
        this.healthCheckTime = healthCheckTime;
    }

    public String getBloodType() {
        return bloodType+"型";
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public ArrayList<LoginInfo> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(ArrayList<LoginInfo> loginInfo) {
        this.loginInfo = loginInfo;
    }

    public WorkHistory getWorkHistory() {
        return workHistory;
    }

    public void setWorkHistory(WorkHistory workHistory) {
        this.workHistory = workHistory;
    }

    public FamilyInfo getFamilyInfo() {
        return familyInfo;
    }

    public void setFamilyInfo(FamilyInfo familyInfo) {
        this.familyInfo = familyInfo;
    }

    public PeaceCard getPeaceCard() {
        return peaceCard;
    }

    public void setPeaceCard(PeaceCard peaceCard) {
        this.peaceCard = peaceCard;
    }

    public PeaceAgentCard getPeaceAgentCard() {
        return peaceAgentCard;
    }

    public void setPeaceAgentCard(PeaceAgentCard peaceAgentCard) {
        this.peaceAgentCard = peaceAgentCard;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public WorkInfo getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(WorkInfo workInfo) {
        this.workInfo = workInfo;
    }

    public TrainingInfo getTrainingInfo() {
        return trainingInfo;
    }

    public void setTrainingInfo(TrainingInfo trainingInfo) {
        this.trainingInfo = trainingInfo;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    public String getTokenKey() {
        return TokenKey;
    }

    public void setTokenKey(String tokenKey) {
        TokenKey = tokenKey;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }
}
