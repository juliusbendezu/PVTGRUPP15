package com.dsv2019.pvt15.prepapp.customcomponents;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.models.PantryItem;

import java.util.List;

public class PantryCategoryView extends LinearLayout {
    public PantryCategoryView(Context context) {
        super(context);
    }

    public PantryCategoryView(Context context, List<PantryItem> items) {
        super(context);
        inflate(context, R.layout.custom_pantry_category_item, this);

        //setSpinnerSolution(items);



        /* Relates to when using center layout with 2 textviews in
         * custom_pantry_category_item.xml
         */

        String category = items.get(0).getCategory();
        TextView titleTv = findViewById(R.id.pantryCategoryTitle);
        titleTv.append(category);

        int amount = items.size();
        TextView amountTv = findViewById(R.id.pantryCategoryAmountTV);
        amountTv.append(String.format("%s st.", amount));


        ImageView imageView = findViewById(R.id.pantryCategoryImage);
        String genCategory = items.get(0).getGeneralCategory();

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

        setOnClickListener(l -> showPantryItems(items));

    }

    private void setSpinnerSolution(List<PantryItem> items) {
        /*
        Spinner spinner = findViewById(R.id.pantryItemsSpinner);
        String[] itemArray = new String[items.size()];
        for (int i = 0; i < itemArray.length; i++) {
            PantryItem item = items.get(i);
            String title = String.format("%s %dg exp. %s",
                    item.getItemName(), item.getAmount(), item.getExpiryDate());
            itemArray[i] = title;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        adapter.add(items.get(0).getCategory());
        adapter.addAll(itemArray);
        spinner.setAdapter(adapter);
        */
    }

    private void showPantryItems(List<PantryItem> items) {
        Toast.makeText(getContext(), items.toString(), Toast.LENGTH_SHORT).show();
    }
}
