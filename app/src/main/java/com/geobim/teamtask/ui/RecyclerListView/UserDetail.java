package com.geobim.teamtask.ui.RecyclerListView;

import android.util.Log;

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
        basicInformation.add(new ChildEntity("用户：", User.getInstance().getRealName()));
        basicInformation.add(new ChildEntity("职位：", User.getInstance().getJobPosition()));
        basicInformation.add(new ChildEntity("性别：", User.getInstance().getSex()));
        basicInformation.add(new ChildEntity("民族：", User.getInstance().getNation()));
        basicInformation.add(new ChildEntity("出生日期", User.getInstance().getBirthday()));
        basicInformation.add(new ChildEntity("身份证：", User.getInstance().getIDCard()));
        basicInformation.add(new ChildEntity("邮箱：", User.getInstance().getEmail()));
        basicInformation.add(new ChildEntity("手机号：", User.getInstance().getPhoneNumber()));
        basicInformation.add(new ChildEntity("用户ID：", User.getInstance().getId()));
        basicInformation.add(new ChildEntity("婚姻状况：", User.getInstance().getMaritalStatus()));
        basicInformation.add(new ChildEntity("籍贯：", User.getInstance().getNativePlace()));
        basicInformation.add(new ChildEntity("户口：", User.getInstance().getLocation()));
        basicInformation.add(new ChildEntity("学历：", User.getInstance().getEducationLevel()));
        basicInformation.add(new ChildEntity("参加工作年份：", User.getInstance().getFirstWorkYear()));
        basicInformation.add(new ChildEntity("体检时间：", User.getInstance().getHealthCheckTime()));
        basicInformation.add(new ChildEntity("血型：", User.getInstance().getBloodType()));
        basicInformation.add(new ChildEntity("QQ：", User.getInstance().getQQ()));
        basicInformation.add(new ChildEntity("注册时间：", User.getInstance().getRegisterDate()));
        groups.add(new ExpandableGroupEntity("基本信息", null, true, basicInformation));
        //登录信息
        ArrayList<ChildEntity> loginInformation = new ArrayList<>();
        ArrayList<LoginInfo> loginInfos = User.getInstance().getLoginInfo();
        if (loginInfos!=null&&loginInfos.size() > 0) {
            loginInformation.add(new ChildEntity("最近登录时间：", loginInfos.get(0).getDateTime()));
            loginInformation.add(new ChildEntity("最近登录IP：", loginInfos.get(0).getiP()));
            loginInformation.add(new ChildEntity("最近登录地址：", loginInfos.get(0).getAddress()));
            loginInformation.add(new ChildEntity("最近登录方式：", loginInfos.get(0).getSystemType()));
            loginInformation.add(new ChildEntity("最近登录设备ID：", loginInfos.get(0).getDeviceId()));
            groups.add(new ExpandableGroupEntity("登录信息", null, true, loginInformation));
        }
        //工作经历
        ArrayList<ChildEntity> workHistory = new ArrayList<>();
        groups.add(new ExpandableGroupEntity("工作经历", null, true, workHistory));


        return groups;
    }
}
