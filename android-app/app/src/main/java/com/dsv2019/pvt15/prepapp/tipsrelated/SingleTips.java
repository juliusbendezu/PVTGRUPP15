package com.dsv2019.pvt15.prepapp.tipsrelated;

public class SingleTips   {

    private String name;
    private String description;
    private int likes;

    public SingleTips(String name, String description) {
        this.description=description;
        this.name=name;

    }

    public void setPopularity(int opinion){
        if(opinion > 0){
            likes ++;
        }else{
            likes--;
        }

    }


    @Override
    public String toString() {
        return String.format(name + " Description: " + description);
    }

    
  //  String.format("%s\n%s", stringDate, title)
}
