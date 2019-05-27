package com.dsv2019.pvt15.prepapp.tipsrelated;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Tip implements Serializable {

    private int id;
    private String title;
    private String description;
    private boolean warmth;
    private boolean water;
    private boolean shelter;
    private boolean food;
    private boolean health;
    private boolean security;
    private boolean storage;
    private boolean other;
    private int likes;
    private String creator;

    @SerializedName("body")
    private String text;

    public Tip(String title, boolean warmth, boolean water, boolean shelter, boolean food, boolean health, boolean security, boolean storage, boolean other, String description, int likes, String creator) {
        this.title = title;
        this.description = description;
        this.warmth = warmth;
        this.water = water;
        this.shelter = shelter;
        this.food = food;
        this.health = health;
        this.security = security;
        this.storage = storage;
        this.other = other;
        this.likes = likes;
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isWarmth() {
        return warmth;
    }

    public boolean isWater() {
        return water;
    }

    public boolean isShelter() {
        return shelter;
    }

    public boolean isFood() {
        return food;
    }

    public boolean isHealth() {
        return health;
    }

    public boolean isSecurity() {
        return security;
    }

    public boolean isStorage() {
        return storage;
    }

    public boolean isOther() {
        return other;
    }

    public int getLikes() {
        return likes;
    }

    public String getCreator() {
        return creator;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWarmth(boolean warmth) {
        this.warmth = warmth;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public void setShelter(boolean shelter) {
        this.shelter = shelter;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public void setHealth(boolean health) {
        this.health = health;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }

    public void setStorage(boolean storage) {
        this.storage = storage;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getCategorys(){
        ArrayList<String> categoryList = new ArrayList<>();
        if(warmth==true){
            categoryList.add("Värme");
        } if(water==true){
            categoryList.add("Vatten");
        } if(shelter==true){
            categoryList.add("Skydd");
        } if(food==true){
            categoryList.add("Mat");
        } if(health==true){
            categoryList.add("Sjukvård");
        } if(security==true){
            categoryList.add("Säkerhet");
        } if(storage==true){
            categoryList.add("Förvaring");
        } if(other==true){
            categoryList.add("Övrigt");
        }
        return categoryList;
    }

    public Tip(String name, String description, boolean[] categoryCheck, String creator) {
        this.description = description;
        this.title = name;
        this.creator = creator;
    }

    public void setLikes(int opinion) {
        if (opinion > 0) {
            likes++;
        } else {
            likes--;
        }
    }
}
