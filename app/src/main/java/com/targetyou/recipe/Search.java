package com.targetyou.recipe;

public class Search {
    String material;
    String amount;
    String unit;
    String date;

    public Search(String material, String amount, String unit, String date) {
        this.material = material;
        this.amount = amount;
        this.unit = unit;
        this.date = date;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
