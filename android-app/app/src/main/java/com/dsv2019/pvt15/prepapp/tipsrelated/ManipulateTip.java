package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dsv2019.pvt15.prepapp.CategoryActivity;
import com.dsv2019.pvt15.prepapp.MainActivity;
import com.dsv2019.pvt15.prepapp.R;

import java.util.ArrayList;

public class ManipulateTip extends Activity {

    ImageButton homeButton;
    ImageButton editButton;
    ImageButton likeButton;
    TextView titleText;
    TextView categoryText;
    TextView descriptionText;
    String title;
    String description;
    ArrayList<String> categoryList;
    int id;
    int likes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_tip);

        title = (String) getIntent().getExtras().get("title");
        description = (String) getIntent().getExtras().get("description");
        categoryList = (ArrayList<String>) getIntent().getExtras().get("categorys");
        id = (int) getIntent().getExtras().get("id");
        likes = (int) getIntent().getExtras().get("likes");

        setHomeButton();
        setTipTitle();
        setCategorys();
        setDescription();
        setEditButton();
        setLikeButton();

    }

    private void setHomeButton() {
        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

    }

    private void setTipTitle() {
        titleText = findViewById(R.id.tipsTitleTextView);
        titleText.setText(title);
    }

    private void setCategorys() {
        String allCategorys = "Tipsets Kategorisering: " + categoryList.get(0);
        categoryText = findViewById(R.id.categoryTextView);
        System.out.println(categoryList.size());
        if (categoryList.size() > 1) {
            for (int i = 1; i < categoryList.size()-1; i++) {
                allCategorys += ", " + categoryList.get(i);
            }
            allCategorys += " & " +categoryList.get(categoryList.size()-1);
        }
        categoryText.setText(allCategorys + ".");
    }

    private void setDescription() {
        descriptionText = findViewById(R.id.tipDescriptionTextView);
        descriptionText.setText(description);
    }

    private void setEditButton(){
        editButton = findViewById(R.id.editImageButton);
        editButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),CreateNewTip.class);
                startIntent.putExtra("source", "MT");
                startIntent.putExtra("id", id);
                startActivity(startIntent);
            }
        });

    }
    private void setLikeButton(){

    }
}
