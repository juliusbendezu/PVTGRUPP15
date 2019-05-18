package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class Tip {

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

    public Tip(String name, String description, boolean[] categoryCheck, String creator) {
        this.description=description;
        this.title=name;
        this.creator=creator;
    }

    public void setLikes(int opinion){
        if(opinion > 0){
            likes ++;
        }else{
            likes--;
        }
    }



    //    private void setCategory(){
//        if (categoryNR == 1){
//            categoryName ="Värme";
//        }else if(categoryNR ==2){
//            categoryName ="Vatten";
//        }else if(categoryNR==3){
//            categoryName ="skydd";
//        }else if(categoryNR==4){
//            categoryName="Mat";
//        }else if(categoryNR==5){
//            categoryName="Sjukvård";
//        }else {
//            categoryName = "Informationssäkerhet";
//        }
//    }

//    public String getName(){
//        return name;
//    }
//    public int getID(){
//        return ID;
//    }

//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Tip singleTips = (Tip) o;
//        return name.equals(singleTips.name) &&
//                description.equals(singleTips.description) &&
//                creator.equals(singleTips.creator) &&
//                ID==(singleTips.ID) &&
//                categoryNR==(singleTips.categoryNR)&&
//                likes == (singleTips.likes);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(ID);
//    }
//
//    @Override
//    public String toString() {
//
//        SpannableString string = new SpannableString(name);
//        string.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return string.toString();
//
//    }
//    @Override
//    public int compareTo(Tip o) {
//        if (o.likes < likes){
//            return 1;
//        }else if(o.likes>likes){
//            return -1;
//        }else{
//            return 0;
//        }
//
//    }
}
