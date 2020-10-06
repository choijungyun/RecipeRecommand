package com.targetyou.recipe;

public class Notice {

    String eatName;
    String eatAmount;
    String eatDate;

    public String getEatName() {
        return eatName;
    }

    public void setEatName(String eatName) {
        this.eatName = eatName;
    }

    public String getEatAmount() {
        return eatAmount;
    }

    public void setEatAmount(String eatAmount) {
        this.eatAmount = eatAmount;
    }

    public String getEatDate() {
        return eatDate;
    }

    public void setEatDate(String eatDate) {
        this.eatDate = eatDate;
    }

    public Notice(String eatName, String eatAmount, String eatDate) {
        this.eatName = eatName;
        this.eatAmount = eatAmount;
        this.eatDate = eatDate;
    }

}