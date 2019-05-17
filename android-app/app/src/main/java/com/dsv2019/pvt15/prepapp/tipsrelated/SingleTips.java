package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.StyleSpan;

import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.models.NewsItem;

import java.util.Objects;

import static android.text.Html.*;

public class SingleTips

        implements Comparable<SingleTips>  {

    private String name;
    private String description;
    private int likes = 0;
    private String creator;
    private int categoryNR;
    private String categoryName;
    private boolean[]categoryCheck;
    private int ID;


    public SingleTips(String name, String description, boolean[] categoryCheck,String creator) {
        this.description=description;
        this.name=name;
        this.categoryCheck=categoryCheck;
        this.creator=creator;
    }

    public void setLikes(int opinion){
        if(opinion > 0){
            likes ++;
        }else{
            likes--;
        }
    }

    private void setCategory(){
        if (categoryNR == 1){
            categoryName ="Värme";
        }else if(categoryNR ==2){
            categoryName ="Vatten";
        }else if(categoryNR==3){
            categoryName ="skydd";
        }else if(categoryNR==4){
            categoryName="Mat";
        }else if(categoryNR==5){
            categoryName="Sjukvård";
        }else {
            categoryName = "Informationssäkerhet";
        }
    }

    public String getName(){
        return name;
    }
    public int getID(){
        return ID;
    }
    public int getCategory(){
        return categoryNR;
    }
    public String getDescription(){
        return description;
    }
    public String getCreator(){
        return creator;
    }
    public int getLikes(){
        return likes;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleTips singleTips = (SingleTips) o;
        return name.equals(singleTips.name) &&
                description.equals(singleTips.description) &&
                creator.equals(singleTips.creator) &&
                ID==(singleTips.ID) &&
                categoryNR==(singleTips.categoryNR)&&
                likes == (singleTips.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {

        SpannableString string = new SpannableString(name);
        string.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string.toString();

//+ID+System.getProperty("line.separator")+ "by: " +creator);
    }
    @Override
    public int compareTo(SingleTips o) {
        if (o.likes < likes){
            return 1;
        }else if(o.likes>likes){
            return -1;
        }else{
            return 0;
        }

    }
}
