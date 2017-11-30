package com.geobim.teamtask.entity;

import java.util.Date;

/**
 * 最近一次工作经历
 * Created by Joseph on 2017/11/30.
 */

public class WorkHistory {
    private Date workTime;                  //时间
    private String place;                   //地点
    private String project;                 //项目
    private String work;                    //从事工作
    private boolean isWorkRailTraffic;      //是否轨道交通工程

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public boolean isWorkRailTraffic() {
        return isWorkRailTraffic;
    }

    public void setWorkRailTraffic(boolean workRailTraffic) {
        isWorkRailTraffic = workRailTraffic;
    }
}
