package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.dsv2019.pvt15.prepapp.tipsrelated.CreateNewTip;
import com.dsv2019.pvt15.prepapp.tipsrelated.SingleTips;
import com.dsv2019.pvt15.prepapp.tipsrelated.TipsItemView;

import static android.graphics.Color.luminance;


public class TipsActivity extends Activity {

    ArrayList<SingleTips> tipsList = new ArrayList<>();
    private int categoryNR;
    private String categoryName;
    private TextView categoryText;
    private ImageButton backButton;
    private ImageButton createNewTipButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        categoryNR =(int) getIntent().getExtras().get("category");

        createBackBtn();
        createNewTipButton();
       // loadTheTips();
        setCategoryView();

    }

    private void setCategoryView(){
        if (categoryNR == 1){
            categoryName ="Värme";
        }else if(categoryNR ==2){
            categoryName ="Vatten";
        }else if(categoryNR==3){
            categoryName ="Skydd";
        }else if(categoryNR==4){
            categoryName="Mat";
        }else if(categoryNR==5){
            categoryName="Sjukvård";
        }else if (categoryNR ==6){
            categoryName = "Informationssäkerhet";
        }else if(categoryNR==7){
            categoryName="Förvaring";
        }else{
            categoryName ="Övrigt";
        }
        categoryText = findViewById(R.id.categoryTextView);
        categoryText.setText(categoryName);

    }

    public void createNewTipButton(){
        createNewTipButton = findViewById(R.id.createNewTipButton);
        createNewTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CreateNewTip.class);
                startActivity(startIntent);
            }
        });
    }

    public void createBackBtn(){
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(startIntent);
            }
        });
    }


//    // denna bör egentligen baseras på categoryNR som vi ska avända för att hämta tipsen ur tabellen.
//    public void loadTheTips(){
//
//        SingleTips singleTipsLong = new SingleTips("Ett långt tips","här är ett tips här är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tipshär är ett tips",categoryNR,"Elsa",1);
//        tipsList.add(singleTipsLong);
//        addTips(singleTipsLong);
//
//        for (int i = 2; i < 10; i++) {
//            SingleTips singleTips = new SingleTips("Ett Tips","här är ett tips",categoryNR,"Elsa",i);
//            tipsList.add(singleTips);
//            addTips(singleTips);
//        }
//    }

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

