package com.targetyou.recipe;

public class Recommend {

    String foodName;
    String gram;
    String carbo;
    String protein;
    String fat;
    String sugar;
    String natrium;
    String cal;
    String result;
    public Recommend(String foodName, String gram, String carbo, String protein, String fat, String sugar, String natrium, String cal, String result) {
        this.foodName = foodName;
        this.gram = gram;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.sugar = sugar;
        this.natrium = natrium;
        this.cal = cal;
        this.result = result;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getGram() {
        return gram;
    }

    public void setGram(String gram) {
        this.gram = gram;
    }

    public String getCarbo() {
        return carbo;
    }

    public void setCarbo(String carbo) {
        this.carbo = carbo;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getNatrium() {
        return natrium;
    }

    public void setNatrium(String natrium) {
        this.natrium = natrium;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}