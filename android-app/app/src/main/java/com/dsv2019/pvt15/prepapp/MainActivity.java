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

    private Fragment defaultFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_test_activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);
        navigation.setItemIconTintList(null);

        //The default fragment that should be loaded when the application starts
        defaultFragment = new NewsFragment();

        //Checks if intent has a requested fragment to go to
        if (!getIntent().hasExtra(SOURCE))
            loadFragment(defaultFragment);
        else
            switch (getIntent().getStringExtra(SOURCE)) {
                case FROM_TIP:
                    loadFragment(new TipsFragment());
                    break;
                case FROM_PANTRY:
                    loadFragment(new PantryFragment());
                    break;
            }


        wakeUpServer();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.menu_news:
                            fragment = new NewsFragment();
                            break;

                        case R.id.menu_tips:
                            fragment = new TipsFragment();
                            break;

                        case R.id.menu_pantry:
                            fragment = new PantryFragment();
                            break;

                        case R.id.menu_download:
                            break;

                        case R.id.menu_map:
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
