package com.dsv2019.pvt15.prepapp.tipsrelated;

import com.dsv2019.pvt15.prepapp.models.NewsItem;

import java.util.Objects;

public class SingleTips

        implements Comparable<SingleTips>  {

    private String name;
    private String description;
    private int likes = 0;
    private String creator;
    private int category;
    private int ID;




    public SingleTips(String name, String description, int category,String creator,int ID) {
        this.description=description;
        this.name=name;
        this.ID=ID;
        this.category=category;
        this.creator=creator;
    }

    public void setLikes(int opinion){
        if(opinion > 0){
            likes ++;
        }else{
            likes--;
        }
    }

    public String getName(){
        return name;
    }
    public int getID(){
        return ID;
    }
    public int getCategory(){
        return category;
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
                category==(singleTips.category)&&
                likes == (singleTips.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return String.format(name + ID + System.getProperty("line.separator")+ "skapad av: "+creator );
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
