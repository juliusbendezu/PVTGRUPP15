package com.dsv2019.pvt15.prepapp.customcomponents;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.models.PantryItem;

import java.util.Set;

public class PantryCategoryView extends LinearLayout {

    private boolean isOpen = false;
    ImageView pantryItemsArrow;

    String type;

    public PantryCategoryView(Context context) {
        super(context);
    }

    public PantryCategoryView(Context context, Set<PantryItem> items) {
        super(context);
        inflate(context, R.layout.custom_pantry_category_item, this);

        String category = items.iterator().next().getCategory();
        TextView titleTv = findViewById(R.id.pantryCategoryTitle);
        titleTv.append(category);

        int amount = items.size();
        TextView amountTv = findViewById(R.id.pantryCategoryAmountTV);
        amountTv.append(String.format("%s st.", amount));


        ImageView imageView = findViewById(R.id.pantryCategoryImage);
        String genCategory = items.iterator().next().getGeneralCategory();
        type = genCategory;

        switch (genCategory) {
            case PantryItem.FOOD_CATEGORY:
                imageView.setImageResource(R.drawable.pantry_fooditem_icon);
                break;
            case PantryItem.MEDICINE_CATEGORY:
                imageView.setImageResource(R.drawable.pantry_medicine_icon);
                break;
            case PantryItem.OTHER_CATEGORY:
                imageView.setImageResource(R.drawable.pantry_other_icon);
                break;
        }

        pantryItemsArrow = findViewById(R.id.showPantryItemsArrow);
        pantryItemsArrow.setOnClickListener(l -> showPantryItems(items));
    }

    private void showPantryItems(Set<PantryItem> items) {
        LinearLayout itemsLayout = findViewById(R.id.pantryCategoryLayout);
        if (!isOpen) {
            pantryItemsArrow.setRotation(90);
            for (PantryItem item : items) {
                itemsLayout.addView(new PantryItemSummaryView(getContext(), item));
            }
            isOpen = true;
        } else {
            pantryItemsArrow.setRotation(0);
            itemsLayout.removeViews(1, itemsLayout.getChildCount() - 1);
            isOpen = false;
        }
    }
}
