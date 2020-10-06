package com.targetyou.recipe;

public class Food {

    String foodName;
    String gram;

    public Food(String foodName, String gram) {
        this.foodName = foodName;
        this.gram = gram;
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
}
