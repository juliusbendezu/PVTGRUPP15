package com.dsv2019.pvt15.prepapp.models;

import java.io.Serializable;

public class PantryItem implements Serializable {

    public static final String FOOD_CATEGORY = "Food";
    public static final String MEDICINE_CATEGORY = "Medicine";
    public static final String OTHER_CATEGORY = "Other";

    private int id;
    private String name;
    private String category;
    private String generalCategory;
    private String expiryDate;
    private int amount;
    private String owner;


    //Without ID
    public PantryItem(String name, String category, String generalCategory, String expiryDate, int amount, String owner) {
        this.name = name;
        this.category = category;
        this.generalCategory = generalCategory;
        this.expiryDate = expiryDate;
        this.amount = amount;
        this.owner = owner;
    }

    //With ID
    public PantryItem(int id, String name, String category, String generalCategory, String expiryDate, int amount, String owner) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.generalCategory = generalCategory;
        this.expiryDate = expiryDate;
        this.amount = amount;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGeneralCategory(){
        return generalCategory;
    }

    public void setGeneralCategory(String generalCategory){
        this.generalCategory = generalCategory;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return String.format("%s %dg %s", name, amount, expiryDate);
    }
}
