package com.dsv2019.pvt15.prepapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PantryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        findViewById(R.id.homeButton).setOnClickListener(l -> startActivity(new Intent(this, MainActivity.class)));
    }
}
