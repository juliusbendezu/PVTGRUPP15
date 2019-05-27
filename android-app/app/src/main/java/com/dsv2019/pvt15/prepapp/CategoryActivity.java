package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.dsv2019.pvt15.prepapp.tipsrelated.CreateNewTip;

public class CategoryActivity extends Activity {

    private Button warmthButton1;
    private Button waterButton2;
    private Button shelterButton3;
    private Button foodButton4;
    private Button healthButton5;
    private Button securityButton6;
    private Button storageButton7;
    private Button otherButton8;
    private ImageButton createNewTip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setWarmthButton();
        setWaterButton();
        setShelterButton();
        setFoodButton();
        setHealthButton();
        setSecurityButton();
        setStorageButton();
        setOtherButton();
        setCreateNewTipButton();
        //setHomeButton();

    }

    private void setWarmthButton() {
        warmthButton1 = findViewById(R.id.warmthButtom);
        warmthButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TipsActivity.class);
                startIntent.putExtra("category", 1);
                startActivity(startIntent);
            }
        });
    }

    private void setWaterButton() {
        waterButton2 = findViewById(R.id.waterButton);
        waterButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TipsActivity.class);
                startIntent.putExtra("category", 2);
                startActivity(startIntent);
            }
        });

    }

    private void setShelterButton() {
        shelterButton3 = findViewById(R.id.shelterButton);
        shelterButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TipsActivity.class);
                startIntent.putExtra("category", 3);
                startActivity(startIntent);
            }
        });

    }

    private void setFoodButton() {
        foodButton4 = findViewById(R.id.foodButton);
        foodButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TipsActivity.class);
                startIntent.putExtra("category", 4);
                startActivity(startIntent);
            }
        });
    }

    private void setHealthButton() {
        healthButton5 = findViewById(R.id.healthButton);
        healthButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TipsActivity.class);
                startIntent.putExtra("category", 5);
                startActivity(startIntent);
            }
        });

    }

    private void setSecurityButton() {
        securityButton6 = findViewById(R.id.securityButton);
        securityButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TipsActivity.class);
                startIntent.putExtra("category", 6);
                startActivity(startIntent);
            }
        });

    }

    private void setStorageButton() {
        storageButton7 = findViewById(R.id.storageButton);
        storageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TipsActivity.class);
                startIntent.putExtra("category", 7);
                startActivity(startIntent);
            }
        });

    }

    private void setOtherButton() {
        otherButton8 = findViewById(R.id.otherButton);
        otherButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TipsActivity.class);
                startIntent.putExtra("category", 8);
                startActivity(startIntent);
            }
        });

    }

    private void setCreateNewTipButton() {
        createNewTip = findViewById(R.id.createNewTipButtonn);
        createNewTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CreateNewTip.class);
                startIntent.putExtra("source", "CA");
                startActivity(startIntent);
            }
        });

    }


}
