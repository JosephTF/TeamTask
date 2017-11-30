package com.geobim.teamtask.entity;

import java.util.Date;

/**
 * 平安卡代理卡
 * Created by Joseph on 2017/11/30.
 */

public class PeaceAgentCard {
    private String agentCardNumber;     //平安卡（代理卡）号
    private Date effectiveDate;         //平安卡（代理卡）生效日期
    private String relWhiteCardNumber;  //关联白卡编号

    public String getAgentCardNumber() {
        return agentCardNumber;
    }

    public void setAgentCardNumber(String agentCardNumber) {
        this.agentCardNumber = agentCardNumber;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getRelWhiteCardNumber() {
        return relWhiteCardNumber;
    }

    public void setRelWhiteCardNumber(String relWhiteCardNumber) {
        this.relWhiteCardNumber = relWhiteCardNumber;
    }
}
