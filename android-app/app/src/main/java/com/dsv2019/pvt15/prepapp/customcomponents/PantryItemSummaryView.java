package com.dsv2019.pvt15.prepapp.customcomponents;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.models.PantryItem;

public class PantryItemSummaryView extends LinearLayout {

    TextView summary;
    ImageView settingsButton;

    public PantryItemSummaryView(Context context, PantryItem pantryItem) {
        super(context);
        inflate(context, R.layout.custom_pantry_item_summary, this);

        summary = findViewById(R.id.pantryItemSummaryTV);
        settingsButton = findViewById(R.id.editPantryItemButton);
        settingsButton.setOnClickListener(l -> showItemSettings());

        summary.append(pantryItem.toString());

    }

    private void showItemSettings() {
        Toast.makeText(getContext(), "Settings clicked", Toast.LENGTH_SHORT).show();
    }
}
