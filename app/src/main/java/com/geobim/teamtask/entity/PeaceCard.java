package com.geobim.teamtask.entity;

/**
 * 正式平安卡号
 * Created by Joseph on 2017/11/30.
 */

public class PeaceCard {
    private String cardNumber;          //平安卡号
    private String ICCardNumber;        //平安IC卡号
    private String workCardNumber;      //平安卡工牌卡号

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getICCardNumber() {
        return ICCardNumber;
    }

    public void setICCardNumber(String ICCardNumber) {
        this.ICCardNumber = ICCardNumber;
    }

    public String getWorkCardNumber() {
        return workCardNumber;
    }

    public void setWorkCardNumber(String workCardNumber) {
        this.workCardNumber = workCardNumber;
    }
}
