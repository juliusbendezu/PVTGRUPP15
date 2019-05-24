package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;

import com.facebook.AccessToken;

import java.util.Objects;

import com.dsv2019.pvt15.prepapp.LoginActivity;
import com.google.gson.annotations.SerializedName;

import static com.facebook.AccessToken.getCurrentAccessToken;

public class Tip
{

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


    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isWarmth()
    {
        return warmth;
    }

    public boolean isWater()
    {
        return water;
    }

    public boolean isShelter()
    {
        return shelter;
    }

    public boolean isFood()
    {
        return food;
    }

    public boolean isHealth()
    {
        return health;
    }

    public boolean isSecurity()
    {
        return security;
    }

    public boolean isStorage()
    {
        return storage;
    }

    public boolean isOther()
    {
        return other;
    }

    public int getLikes()
    {
        return likes;
    }

    public String getCreator()
    {
        return creator;
    }

    public String getText()
    {
        return text;
    }

    public Tip(String name, String description, boolean[] categoryCheck, String creator)
    {
        this.description = description;
        this.title = name;
        this.creator = creator;
    }

    public void setLikes(int opinion)
    {
        if (opinion > 0) {
            likes++;
        } else {
            likes--;
        }
    }
}
