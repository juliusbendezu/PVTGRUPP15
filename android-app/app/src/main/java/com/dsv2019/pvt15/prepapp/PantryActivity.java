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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantryActivity extends Activity {

    private final String EMPTY_PANTRY_MSG = "Du har inget i förrådet! Lägg till något?";

    LinearLayout layout;
    Button addItemButton;

    List<PantryItem> pantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        findViewById(R.id.homeButton).setOnClickListener(l -> startActivity(new Intent(this, MainActivity.class)));

        layout = findViewById(R.id.pantryActivityItemsLayout);
        addItemButton = findViewById(R.id.addPantryItemButton);
        addItemButton.setOnClickListener(l -> addPantryItem());

        getPantry();

    }

    private void addPantryItem() {
        startActivity(new Intent(this, PantryAddItemForm.class));
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

        Map<String, Set<PantryItem>> pantryMap = new TreeMap<>();

        for (PantryItem p : pantry) {
            String category = p.getCategory();
            if (pantryMap.containsKey(category)) {
                pantryMap.get(category).add(p);
            } else {
                Set<PantryItem> set = new TreeSet<>();
                set.add(p);
                pantryMap.put(category, set);
            }
        }

        for (Set<PantryItem> category : pantryMap.values())
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

    private void showPantryByType(String type) {
        for(int i = 0; i < layout.getChildCount(); i++){
            PantryCategoryView view = (PantryCategoryView) layout.getChildAt(i);
            if(!(view.getType().equals(type)))
                layout.removeViewAt(i);
        }
    }
}
