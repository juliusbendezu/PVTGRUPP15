package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private ImageButton mapsButton;
    private ImageButton newsButton;
    private ImageButton tipsButton;
    private ImageButton pantryButton;
    private ImageButton facebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapsButton = findViewById(R.id.mapsButton);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(startIntent);
            }
        });

        newsButton = findViewById(R.id.newsButton);
        newsButton.setOnClickListener(l ->
        {
            Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
            startActivity(intent);
        });

        tipsButton = findViewById(R.id.tipsButton);
        tipsButton.setOnClickListener(l ->
        {
            Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
            startActivity(intent);
        });

        pantryButton = findViewById(R.id.pantryButton);
        pantryButton.setOnClickListener(l -> startActivity(new Intent(this, PantryActivity.class)));

        facebookButton = findViewById(R.id.facebook_test_button);
        facebookButton.setOnClickListener(v ->
        {
            Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(startIntent);
        });

        wakeUpServer();

    }

    private void wakeUpServer() {
        BaseAPIService api = RetrofitClient.getApiService();
        Call<String> call = api.wakeServer();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful())
                    System.out.println("Server did not respond");
                else
                    System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
