package com.dsv2019.pvt15.prepapp.models;

public class PantryItem {
    private String name;
    private String category;
    private String expiryDate;

    public PantryItem(String name, String category, String expiryDate) {
        this.name = name;
        this.category = category;
        this.expiryDate = expiryDate;
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
}
