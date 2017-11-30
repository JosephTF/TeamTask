package com.geobim.teamtask.entity;

import java.util.Date;

/**
 * 特种作业证
 * Created by Joseph on 2017/11/30.
 */

public class SpecialWorkPermit {
    private String cardNumber;          //证件号
    private String issuingAuthority;    //发证机关
    private Date effectiveDate;         //生效日期
    private Date expirationDate;        //失效日期

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
