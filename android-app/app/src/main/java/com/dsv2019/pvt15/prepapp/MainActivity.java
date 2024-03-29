package com.dsv2019.pvt15.prepapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    public static final String SOURCE = "source";
    public static final String FROM_TIP = "fromTip";
    public static final String FROM_PANTRY = "fromPantry";
    public static final String CREATOR_NAME = "manprepper_55";


    public static final int TIP_INDEX = 2;
    public static final int PANTRY_INDEX = 3;

    private Fragment defaultFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);
        navigation.setItemIconTintList(null);

        navigation.getMenu().findItem(R.id.menu_news).setChecked(true);


        wakeUpServer();
        //The default fragment that should be loaded when the application starts
        defaultFragment = new NewsFragment();

        //Checks if intent has a requested fragment to go to
        if (!getIntent().hasExtra(SOURCE))
            loadFragment(defaultFragment);
        else
            switch (getIntent().getStringExtra(SOURCE)) {
                case FROM_TIP:
                    navigation.getMenu().getItem(TIP_INDEX).setChecked(true);
                    loadFragment(new TipsFragment());
                    break;
                case FROM_PANTRY:
                    navigation.getMenu().getItem(PANTRY_INDEX).setChecked(true);
                    loadFragment(new PantryFragment());
                    break;
            }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.menu_news:
                            //menuItem.setChecked(true);
                            fragment = new NewsFragment();
                            break;

                        case R.id.menu_tips:
                           // menuItem.setChecked(true);
                            fragment = new TipsFragment();
                            break;

                        case R.id.menu_pantry:
                            //menuItem.setChecked(true);
                            fragment = new PantryFragment();
                            break;

                        case R.id.menu_download:
                            //menuItem.setChecked(true);
                            fragment = new DownloadFragment();
                            break;

                        case R.id.menu_map:
                            //menuItem.setChecked(true);
                            fragment = new MapFragment();
                            break;
                    }
                    return loadFragment(fragment);
                }
            };

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
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
