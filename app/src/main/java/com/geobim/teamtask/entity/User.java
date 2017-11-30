package com.geobim.teamtask.entity;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * 用户信息
 * Created by Josph on 2017/11/30.
 */

public class User {
    private String ID;                              //Id
    private String IDCard;                          //身份证
    private String email;                           //邮箱，可作为用户名登录
    private String phoneNumber;                     //手机号，可作为用户名登录
    private String realName;                        //用户姓名
    private String avatar;                          //用户头像文件Id
    private String jobPosition;                     //工作职位
    private int sex;                                //性别：0-未透露；1-男性；2-女性
    private String nation;                          //民族
    private Date birthday;                          //出生日期
    private String maritalStatus;                   //婚姻状况
    private String nativePlace;                     //籍贯
    private String location;                        //户口所在地
    private String educationLevel;                  //教育程度
    private int firstWorkYear;                      //参加工作年份
    private Date healthCheckTime;                   //体检时间
    private String bloodType;                       //血型
    private String QQ;                              //qq号
    private Date registerDate;                      //用户注册时间
    private ArrayList<LoginInfo> loginInfo;         //用户最近登录信息，最多保留100条数据
    private WorkHistory workHistory;                //最近一次工作经历
    private FamilyInfo familyInfo;                  //家属信息
    private PeaceCard peaceCard;                    //正式平安卡号
    private PeaceAgentCard  peaceAgentCard;         //平安卡代理卡
    private CompanyInfo companyInfo;                //单位信息
    private WorkInfo workInfo;                      //工种信息
    private TrainingInfo trainingInfo;              //培训信息

}
