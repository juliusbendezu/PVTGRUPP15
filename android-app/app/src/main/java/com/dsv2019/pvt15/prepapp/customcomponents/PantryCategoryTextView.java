package com.dsv2019.pvt15.prepapp.customcomponents;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsv2019.pvt15.prepapp.models.PantryItem;

import java.util.List;

public class PantryCategoryTextView extends LinearLayout {
    public PantryCategoryTextView(Context context) {
        super(context);
    }

    public PantryCategoryTextView(Context context, List<PantryItem> items){
        super(context);

        String category = items.get(0).getCategory();
        TextView titleTv = new TextView(context);
        titleTv.append(category);
        addView(titleTv);

        int amount = items.size();
        TextView amountTv = new TextView(context);
        amountTv.append(String.format("%s st.", amount));
        addView(amountTv);
    }
}
