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

public class ManipulateTip extends Activity {

    ImageButton homeButton;
    ImageButton editButton;
    ImageButton likeButton;
    TextView titleText;
    TextView categoryText;
    TextView descriptionText;
    String title;
    String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_tip);

        title = (String) getIntent().getExtras().get("title");
        description = (String) getIntent().getExtras().get("description");


        setHomeButton();
        setTipTitle();
        setCategorys();
        setDescription();

    }
    private void setHomeButton(){
        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

    }
    private void setTipTitle(){
        titleText = findViewById(R.id.tipsTitleTextView);
        titleText.setText(title);

    }
    private void setCategorys(){


    }
    private void setDescription(){
        descriptionText = findViewById(R.id.tipDescriptionTextView);
        descriptionText.setText(description);

    }
}
