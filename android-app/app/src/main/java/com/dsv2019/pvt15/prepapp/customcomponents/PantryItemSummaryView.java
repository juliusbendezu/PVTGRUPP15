package com.dsv2019.pvt15.prepapp.customcomponents;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.PantryFragment;
import com.dsv2019.pvt15.prepapp.PantryAddItemForm;
import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.InternetConnection;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;
import com.dsv2019.pvt15.prepapp.models.PantryItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantryItemSummaryView extends LinearLayout {

    TextView summary;
    ImageView settingsButton;

    private PantryItem pantryItem;

    public PantryItemSummaryView(Context context) {
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
        Context context = getContext();

        Intent intent = new Intent(context, PantryAddItemForm.class);
        intent.putExtra(PantryItem.KEY, pantryItem);
        context.startActivity(intent);
    }

    private void deletePantryItem() {
        Context context = getContext();
        if (!InternetConnection.checkConnection(context)) {
            Toast.makeText(context, "Ingen internetuppkoppling, pröva igen senare..", Toast.LENGTH_SHORT).show();
            return;
        }

        BaseAPIService api = RetrofitClient.getApiService();
        Call<PantryItem> call = api.deletePantryItem(pantryItem.getId());
        call.enqueue(new Callback<PantryItem>() {
            @Override
            public void onResponse(Call<PantryItem> call, Response<PantryItem> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Kunde inte ta bort tips, pröva igen senare..", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PantryItem> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
