package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity
{

    private ImageButton mapsButton;
    private ImageButton newsButton;
<<<<<<< HEAD
=======
    private ImageButton tipsButton;
>>>>>>> 82ceeb4e07acdd97762dcd73271c6a7e8259d024

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapsButton = findViewById(R.id.mapsButton);
        mapsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startIntent = new Intent(getApplicationContext(), MapsActivity2.class);
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
        tipsButton.setOnClickListener(l -> {
            Intent intent = new Intent(getApplicationContext(), TipsActivity.class);
            startActivity(intent);
        });


    }
}
