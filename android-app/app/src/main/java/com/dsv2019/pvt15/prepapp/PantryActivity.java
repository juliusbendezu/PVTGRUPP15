package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.dsv2019.pvt15.prepapp.customcomponents.PantryCategoryView;
import com.dsv2019.pvt15.prepapp.models.PantryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PantryActivity extends Activity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        findViewById(R.id.homeButton).setOnClickListener(l -> startActivity(new Intent(this, MainActivity.class)));

        layout = findViewById(R.id.pantryActivityItemsLayout);


        List<PantryItem> pantry = new ArrayList<>();
        pantry.add(new PantryItem("Näskaffe", "Kaffe", "2020-05-04", PantryItem.FOOD_CATEGORY));
        pantry.add(new PantryItem("Pastiller", "Tabletter", "2020-05-04", PantryItem.MEDICINE_CATEGORY));
        pantry.add(new PantryItem("Såg", "Verktyg", "", PantryItem.OTHER_CATEGORY));
        pantry.add(new PantryItem("Lyxkaffe", "Kaffe", "2022", PantryItem.FOOD_CATEGORY));

        addPantry(pantry);
    }

    private void addPantry(List<PantryItem> pantry) {
        Map<String, List<PantryItem>> categories = new TreeMap<>();
        for (PantryItem p : pantry) {
            String category = p.getCategory();
            if (categories.containsKey(category)) {
                categories.get(category).add(p);
            } else {
                List<PantryItem> list = new ArrayList<>();
                list.add(p);
                categories.put(category, list);
            }
        }

        for (List<PantryItem> category : categories.values())
            layout.addView(new PantryCategoryView(this, category));
    }
}
