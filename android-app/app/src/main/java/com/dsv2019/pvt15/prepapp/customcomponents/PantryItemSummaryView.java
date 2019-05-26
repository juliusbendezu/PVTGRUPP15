package com.dsv2019.pvt15.prepapp.customcomponents;

import android.content.Context;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.models.PantryItem;

public class PantryItemSummaryView extends LinearLayout {

    TextView summary;
    ImageView settingsButton;

    private PantryItem pantryItem;

    public PantryItemSummaryView(Context context){
        super(context);
    }

    public PantryItemSummaryView(Context context, PantryItem pantryItem) {
        super(context);
        inflate(context, R.layout.custom_pantry_item_summary, this);

        this.pantryItem = pantryItem;

        summary = findViewById(R.id.pantryItemSummaryTV);
        settingsButton = findViewById(R.id.editPantryItemButton);
        settingsButton.setOnClickListener(l -> showItemSettings());

        summary.append(pantryItem.toString());

    }

    private void showItemSettings() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_edit_pantry_item, null);
        PopupWindow popup = new PopupWindow(popupView,
                (int) (getWidth() * .8),
                LayoutParams.WRAP_CONTENT,
                true);
        setupPopup(popup);
        popup.showAtLocation(this, Gravity.CENTER, (int) settingsButton.getX(), (int) settingsButton.getY());

    }

    private void setupPopup(PopupWindow popup) {
        TextView title = popup.getContentView().findViewById(R.id.popupPantryItemTitle);
        LinearLayout edit = popup.getContentView().findViewById(R.id.editPantryItem);
        LinearLayout delete = popup.getContentView().findViewById(R.id.deletePantryItem);

        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title.append(summary.getText());
        edit.setOnClickListener(l -> editPantryItem());
        delete.setOnClickListener(l -> deletePantryItem());
    }

    private void editPantryItem() {
        System.out.println(pantryItem.getId());
        //Make PUT request to API
    }

    private void deletePantryItem() {
        System.out.println(pantryItem.getId());
        //Make DELETE request to API
    }
}
