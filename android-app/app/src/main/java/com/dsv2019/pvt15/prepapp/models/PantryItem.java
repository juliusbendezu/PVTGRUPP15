package com.dsv2019.pvt15.prepapp.models;

public class PantryItem {

    public static final String FOOD_CATEGORY = "Food";
    public static final String MEDICINE_CATEGORY = "Medicine";
    public static final String OTHER_CATEGORY = "Other";

    private String name;
    private String category;
    private String expiryDate;
    private String generalCategory;
    private int amount;

    public PantryItem(String name, String category, String expiryDate, String generalCategory, int amount) {
        this.name = name;
        this.category = category;
        this.expiryDate = expiryDate;
        this.generalCategory = generalCategory;
        this.amount = amount;
    }

    public String getItemName() {
        return name;
    }

    public void setItemName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getGeneralCategory(){
        return generalCategory;
    }

    public void setGeneralCategory(String generalCategory){
        this.generalCategory = generalCategory;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("%s %dg %s", name, amount, expiryDate);
    }
}
