package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import com.dsv2019.pvt15.prepapp.customcomponents.NewsItemView;
import com.dsv2019.pvt15.prepapp.models.NewsItem;
import com.dsv2019.pvt15.prepapp.tipsrelated.SingleTips;
import com.dsv2019.pvt15.prepapp.tipsrelated.TipsItemView;


public class TipsActivity extends Activity {

    ArrayList<SingleTips> tipsList = new ArrayList<>();

    private ImageButton homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        createHomeBtn();

        loadTheTips();
    }
    public void createHomeBtn(){
        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });
    }

    public void loadTheTips(){

        SingleTips singleTipsLong = new SingleTips("Ett långt tips","här är ett tips här är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tips",1,"Elsa",1);
        tipsList.add(singleTipsLong);
        addTips(singleTipsLong);

        for (int i = 2; i < 10; i++) {
            SingleTips singleTips = new SingleTips("name","här är ett tips",1,"Elsa",i);
            tipsList.add(singleTips);
            addTips(singleTips);
        }
    }

    private void addTips(SingleTips singleTips) {
        LinearLayout layout = findViewById(R.id.newsLinearLayout);

        TipsItemView tipsItemView = new TipsItemView(this, singleTips);

        tipsItemView.setOnClickListener(l -> {
            if (tipsItemView.getBackground() == null)
                tipsItemView.setBackgroundColor(Color.CYAN);
            else
                tipsItemView.setBackground(null);
        });

        layout.addView(tipsItemView);
    }
}

