package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.InternetConnection;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;
import com.dsv2019.pvt15.prepapp.customcomponents.PantryCategoryView;
import com.dsv2019.pvt15.prepapp.models.PantryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantryActivity extends Activity {

    private final String EMPTY_PANTRY_MSG = "Du har inget i förrådet! Lägg till något?";
    LinearLayout layout;
    Button addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        findViewById(R.id.homeButton).setOnClickListener(l -> startActivity(new Intent(this, MainActivity.class)));

        layout = findViewById(R.id.pantryActivityItemsLayout);
        addItemButton = findViewById(R.id.addPantryItemButton);
        addItemButton.setOnClickListener(l -> addPantryItem());

//      List<PantryItem> pantry = new ArrayList<>();
//      pantry.add(new PantryItem("Näskaffe", "Kaffe", "2020-05-04", PantryItem.FOOD_CATEGORY, 500));
//      pantry.add(new PantryItem("Pastiller", "Tabletter", "2020-05-04", PantryItem.MEDICINE_CATEGORY, 200));
//      pantry.add(new PantryItem("Såg", "Verktyg", "", PantryItem.OTHER_CATEGORY, 1000));
//      pantry.add(new PantryItem("Lyxkaffe", "Kaffe", "2022", PantryItem.FOOD_CATEGORY, 450));
//      showPantry(pantry);

        getPantry();

    }

    private void addPantryItem() {
        startActivity(new Intent(this, PantryAddItemForm.class));

        //Show form for adding pantryitem
        //Make a post request using retrofit
        //Refresh to show new item
    }

    private void getPantry() {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading pantry..");
        dialog.setMessage("please wait");
        dialog.show();

        if (!InternetConnection.checkConnection(this)) {
            Toast.makeText(this, "Could not load pantry, please try again", Toast.LENGTH_SHORT).show();
        }

        BaseAPIService apiService = RetrofitClient.getApiService();
        Call<List<PantryItem>> call = apiService.getPantry();
        call.enqueue(new Callback<List<PantryItem>>() {
            @Override
            public void onResponse(Call<List<PantryItem>> call, Response<List<PantryItem>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PantryActivity.this, "Could not load pantry, please try again", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                List<PantryItem> pantry;
                pantry = response.body();
                showPantry(pantry);
            }

            @Override
            public void onFailure(Call<List<PantryItem>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(PantryActivity.this, "Could not load pantry, please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showPantry(List<PantryItem> pantry) {
        if (pantry == null || pantry.isEmpty()) {
            showEmptyPantryMsg();
            return;
        }

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

    private void showEmptyPantryMsg() {
        TextView empty = new TextView(this);
        empty.append(EMPTY_PANTRY_MSG);
        empty.setGravity(Gravity.CENTER);
        empty.setTypeface(empty.getTypeface(), Typeface.BOLD);

        empty.setPadding(20, 100, 20, 100);
        layout.addView(empty);
    }
}
