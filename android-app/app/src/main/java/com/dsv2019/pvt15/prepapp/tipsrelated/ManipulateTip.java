package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dsv2019.pvt15.prepapp.MainActivity;
import com.dsv2019.pvt15.prepapp.R;

public class ManipulateTip extends AppCompatActivity {

    ImageButton homeButton;
    ImageButton editButton;
    ImageButton likeButton;
    TextView titleText;
    TextView categoryText;
    TextView descriptionText;
    Tip oldTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_tip);

        oldTip = (Tip) getIntent().getSerializableExtra("theTip");
        System.out.println(oldTip.getTitle());

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
        System.out.println(oldTip.getTitle());
        titleText.setText(oldTip.getTitle());
    }

    private void setCategorys() {
        String allCategorys = "Tipsets Kategorisering: " + oldTip.getCategorys().get(0);
        categoryText = findViewById(R.id.categoryTextView);
        System.out.println(oldTip.getCategorys().size());
        if (oldTip.getCategorys().size() > 1) {
            for (int i = 1; i < oldTip.getCategorys().size() - 1; i++) {
                allCategorys += ", " + oldTip.getCategorys().get(i);
            }
            allCategorys += " & " + oldTip.getCategorys().get(oldTip.getCategorys().size() - 1);
        }
        categoryText.setText(allCategorys + ".");
    }

    private void setDescription() {
        descriptionText = findViewById(R.id.tipDescriptionTextView);
        descriptionText.setText(oldTip.getDescription());
    }

    private void setEditButton() {
        editButton = findViewById(R.id.editImageButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CreateNewTip.class);
                startIntent.putExtra("source", "MT");
                startIntent.putExtra("theTip", oldTip);
                startActivity(startIntent);
            }
        });

    }

    private void setLikeButton() {

    }
}
