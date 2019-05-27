package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.InternetConnection;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;
import com.dsv2019.pvt15.prepapp.tipsrelated.CreateNewTip;
import com.dsv2019.pvt15.prepapp.tipsrelated.ManipulateTip;
import com.dsv2019.pvt15.prepapp.tipsrelated.Tip;
import com.dsv2019.pvt15.prepapp.tipsrelated.TipsItemView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TipsActivity extends Activity {

    ArrayList<Tip> tipsList = new ArrayList<>();
    Tip[] newListToSort;
    private int categoryNR;
    private String categoryName;
    private TextView categoryText;
    private ImageButton backButton;
    private ImageButton createNewTipButton;
    private ArrayList<String> categoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        categoryNR = (int) getIntent().getExtras().get("category");

        createBackBtn();
        createNewTipButton();
        loadTheTips();
        setCategoryView();
    }

    private void setCategoryView() {

        if (categoryNR == 1) {
            categoryName = "Värme";

        } else if (categoryNR == 2) {
            categoryName = "Vatten";

        } else if (categoryNR == 3) {
            categoryName = "Skydd";

        } else if (categoryNR == 4) {
            categoryName = "Mat";

        } else if (categoryNR == 5) {
            categoryName = "Sjukvård";

        } else if (categoryNR == 6) {
            categoryName = "Säkerhet";

        } else if (categoryNR == 7) {
            categoryName = "Förvaring";

        } else {
            categoryName = "Övrigt";

        }
        categoryText = findViewById(R.id.categoryTextView);
        categoryText.setText(categoryName);
    }

    public void createNewTipButton() {
        createNewTipButton = findViewById(R.id.createNewTipButton);
        createNewTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CreateNewTip.class);
                startIntent.putExtra("source", "TA");
                startActivity(startIntent);
            }
        });
    }

    public void createBackBtn() {
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(startIntent);
            }
        });
    }


    public void loadTheTips() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;

            dialog = new ProgressDialog(TipsActivity.this);
            dialog.setTitle("Getting the tips");
            dialog.setMessage("please wait");
            dialog.show();


            BaseAPIService api = RetrofitClient.getApiService();

            Call<List<Tip>> call = api.getTips();
            //Call<String> call =api.getHelloString();

            call.enqueue(new Callback<List<Tip>>() {
                @Override
                public void onResponse(Call<List<Tip>> call, Response<List<Tip>> response) {
                    dialog.dismiss();
                    if (!response.isSuccessful()) {
                        Toast.makeText(TipsActivity.this, "Tipsen har inte laddats" + response.code(), Toast.LENGTH_LONG).show();
                        call.clone().enqueue(this);
                    }

                    List<Tip> allTips = response.body();
                    for (int i = 0; i < allTips.size(); i++) {
                        Tip tipToCheck = allTips.get(i);
                        checkTheTipsCategoryandAdd(tipToCheck);
                    }
                    sort(tipsList);


                    for (int i = 0; i < newListToSort.length; i++) {
                        System.out.println(newListToSort[i].getTitle() + " i loopen");
                        addTips(newListToSort[i]);
                    }

                }

                @Override
                public void onFailure(Call<List<Tip>> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(TipsActivity.this, "Tipsen har inte laddats2", Toast.LENGTH_LONG).show();
                    call.clone().enqueue(this);
                }
            });
        }
    }

    private void sort(List<Tip> listToSort) {

        newListToSort = new Tip[listToSort.size()];
        int n = listToSort.size();
        for (int u = 0; u < listToSort.size(); u++) {
            newListToSort[u] = listToSort.get(u);
        }

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (newListToSort[j].equals(newListToSort[min_idx])) {
                    if (newListToSort[j].getLikes() < newListToSort[min_idx].getLikes()) {
                        newListToSort[min_idx] = newListToSort[j];
                    }
                }
            }


            // Swap the found minimum element with the first
            // element

            Tip temp = newListToSort[min_idx];
            newListToSort[min_idx] = newListToSort[i];
            newListToSort[i] = temp;
        }

    }

    private void checkTheTipsCategoryandAdd(Tip tipToCheck) {

        if (categoryNR == 1) {
            if (tipToCheck.isWarmth() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 2) {
            if (tipToCheck.isWater() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 3) {
            if (tipToCheck.isShelter() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 4) {
            if (tipToCheck.isFood() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 5) {
            if (tipToCheck.isHealth() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 6) {
            if (tipToCheck.isSecurity() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 7) {
            if (tipToCheck.isStorage() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 8) {
            if (tipToCheck.isOther() == true) {
                tipsList.add(tipToCheck);
            }
        }
    }


    private void addTips(Tip tip) {
        LinearLayout layout = findViewById(R.id.newsLinearLayout);

        TipsItemView tipsItemView = new TipsItemView(this, tip);

        tipsItemView.setOnClickListener(l -> {
                Intent startIntent = new Intent(getApplicationContext(), ManipulateTip.class);
                startIntent.putExtra("tip",tip);

                startIntent.putExtra("title", tip.getTitle());
                startIntent.putExtra("description", tip.getDescription());
                startIntent.putExtra("id",tip.getId());
                startIntent.putExtra("likes", tip.getLikes());
                startIntent.putStringArrayListExtra("categorys", tip.getCategorys());
                startActivity(startIntent);

        });

        layout.addView(tipsItemView);
    }
}

