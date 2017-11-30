package com.geobim.teamtask.entity;

import java.util.Date;

/**
 * 家属信息
 * Created by Joseph on 2017/11/30.
 */

public class FamilyInfo {
    private String name;            //姓名
    private String relationship;    //关系
    private Date birthday;          //出生年月
    private String telephone;       //联系电话

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
