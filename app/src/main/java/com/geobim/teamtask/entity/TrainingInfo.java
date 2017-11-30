package com.geobim.teamtask.entity;

import java.util.Date;

/**
 * 培训信息
 * Created by Joseph on 2017/11/30.
 */

public class TrainingInfo {
    private Date firstSafeEduTime;              //一级安全教育时间
    private Date secondSafeEduTime;             //二级安全教育时间
    private Date threeSafeEduTime;              //三级安全教育时间
    private Date safeTeachDiscloseTime;         //安全技术交底时间
    private Date oneOnOneTime;                  //一对一面谈时间

    public Date getFirstSafeEduTime() {
        return firstSafeEduTime;
    }

    public void setFirstSafeEduTime(Date firstSafeEduTime) {
        this.firstSafeEduTime = firstSafeEduTime;
    }

    public Date getSecondSafeEduTime() {
        return secondSafeEduTime;
    }

    public void setSecondSafeEduTime(Date secondSafeEduTime) {
        this.secondSafeEduTime = secondSafeEduTime;
    }

    public Date getThreeSafeEduTime() {
        return threeSafeEduTime;
    }

    public void setThreeSafeEduTime(Date threeSafeEduTime) {
        this.threeSafeEduTime = threeSafeEduTime;
    }

    public Date getSafeTeachDiscloseTime() {
        return safeTeachDiscloseTime;
    }

    public void setSafeTeachDiscloseTime(Date safeTeachDiscloseTime) {
        this.safeTeachDiscloseTime = safeTeachDiscloseTime;
    }

    public Date getOneOnOneTime() {
        return oneOnOneTime;
    }

    public void setOneOnOneTime(Date oneOnOneTime) {
        this.oneOnOneTime = oneOnOneTime;
    }
}
