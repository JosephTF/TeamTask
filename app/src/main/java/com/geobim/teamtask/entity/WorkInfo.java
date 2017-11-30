package com.geobim.teamtask.entity;

/**
 * 工种信息
 * Created by Joseph on 2017/11/30.
 */

public class WorkInfo {
    private String workName;                            //工种
    private boolean isSpecialWork;                      //是否特殊工种
    private SpecialWorkPermit specialWorkPermit;        //特种作业证

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public boolean isSpecialWork() {
        return isSpecialWork;
    }

    public void setSpecialWork(boolean specialWork) {
        isSpecialWork = specialWork;
    }

    public SpecialWorkPermit getSpecialWorkPermit() {
        return specialWorkPermit;
    }

    public void setSpecialWorkPermit(SpecialWorkPermit specialWorkPermit) {
        this.specialWorkPermit = specialWorkPermit;
    }
}
