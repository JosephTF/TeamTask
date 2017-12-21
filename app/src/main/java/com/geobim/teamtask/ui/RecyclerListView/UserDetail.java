package com.geobim.teamtask.ui.RecyclerListView;

import com.geobim.teamtask.entity.LoginInfo;
import com.geobim.teamtask.entity.User;

import java.util.ArrayList;

/**
 * 用户详细信息列表
 * Created by Administrator on 2017/12/20.
 */

public class UserDetail extends GroupModel {
    public static ArrayList<ExpandableGroupEntity> getUserDetail() {
        ArrayList<ExpandableGroupEntity> groups = new ArrayList<>();
        //基本信息
        ArrayList<ChildEntity> basicInformation = new ArrayList<>();
        basicInformation.add(new ChildEntity("用户", User.getInstance().getRealName()));
        basicInformation.add(new ChildEntity("性别", User.getInstance().getSex()));
        basicInformation.add(new ChildEntity("民族", User.getInstance().getNation()));
        basicInformation.add(new ChildEntity("出生日期", User.getInstance().getBirthday()));
        basicInformation.add(new ChildEntity("身份证", User.getInstance().getIDCard()));
        basicInformation.add(new ChildEntity("邮箱", User.getInstance().getEmail()));
        basicInformation.add(new ChildEntity("手机号", User.getInstance().getPhoneNumber()));
        basicInformation.add(new ChildEntity("用户ID", User.getInstance().getId()));
        basicInformation.add(new ChildEntity("婚姻状况", User.getInstance().getMaritalStatus()));
        basicInformation.add(new ChildEntity("籍贯", User.getInstance().getNativePlace()));
        basicInformation.add(new ChildEntity("户口", User.getInstance().getLocation()));
        basicInformation.add(new ChildEntity("学历", User.getInstance().getEducationLevel()));
        basicInformation.add(new ChildEntity("工作年份", User.getInstance().getFirstWorkYear()));
        basicInformation.add(new ChildEntity("体检时间", User.getInstance().getHealthCheckTime()));
        basicInformation.add(new ChildEntity("血型", User.getInstance().getBloodType()));
        basicInformation.add(new ChildEntity("注册时间", User.getInstance().getRegisterDate()));
        groups.add(new ExpandableGroupEntity("基本信息", null, true, basicInformation));
        //登录信息
        ArrayList<ChildEntity> loginInformation = new ArrayList<>();
        loginInformation.add(new ChildEntity("登录时间", "2017-12-21"));
        loginInformation.add(new ChildEntity("登录IP", "124.16.0.109"));
        loginInformation.add(new ChildEntity("登录地址", "中国广东省广州市 移动"));
        loginInformation.add(new ChildEntity("登录方式", "安卓手机"));
        loginInformation.add(new ChildEntity("设备ID", "暂无"));
        groups.add(new ExpandableGroupEntity("登录信息", null, true, loginInformation));
        //工作经历
        ArrayList<ChildEntity> workHistory = new ArrayList<>();
        workHistory.add(new ChildEntity("时间","2016-2-15"));
        workHistory.add(new ChildEntity("地点","北京"));
        workHistory.add(new ChildEntity("项目","账目小计APP研发"));
        workHistory.add(new ChildEntity("从事工作","管理研发"));
        workHistory.add(new ChildEntity("轨道交通","否"));
        groups.add(new ExpandableGroupEntity("工作经历", null, true, workHistory));
        //平安卡
        ArrayList<ChildEntity> peaceCard = new ArrayList<>();
        peaceCard.add(new ChildEntity("卡号","115023267"));
        peaceCard.add(new ChildEntity("IC号","00EBE683"));
        peaceCard.add(new ChildEntity("工牌号","EBE683"));
        groups.add(new ExpandableGroupEntity("平安卡", null, true, peaceCard));
        return groups;
    }
}
