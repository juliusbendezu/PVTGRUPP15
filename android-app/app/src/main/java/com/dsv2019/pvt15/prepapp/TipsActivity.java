package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.dsv2019.pvt15.prepapp.tipsrelated.BaseAPIService;
import com.dsv2019.pvt15.prepapp.tipsrelated.CreateNewTip;
import com.dsv2019.pvt15.prepapp.tipsrelated.Tip;
import com.dsv2019.pvt15.prepapp.tipsrelated.TipsItemView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.luminance;


public class TipsActivity extends Activity {

    ArrayList<Tip> tipsList = new ArrayList<>();
    private int categoryNR;
    private String categoryName;
    private TextView categoryText;
    private ImageButton backButton;
    private ImageButton createNewTipButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        categoryNR =(int) getIntent().getExtras().get("category");

        createBackBtn();
        createNewTipButton();
        loadTheTips();
        setCategoryView();


    }

    private void setCategoryView(){
        if (categoryNR == 1){
            categoryName ="Värme";
        }else if(categoryNR ==2){
            categoryName ="Vatten";
        }else if(categoryNR==3){
            categoryName ="Skydd";
        }else if(categoryNR==4){
            categoryName="Mat";
        }else if(categoryNR==5){
            categoryName="Sjukvård";
        }else if (categoryNR ==6){
            categoryName = "Informationssäkerhet";
        }else if(categoryNR==7){
            categoryName="Förvaring";
        }else{
            categoryName ="Övrigt";
        }
        categoryText = findViewById(R.id.categoryTextView);
        categoryText.setText(categoryName);

    }

    public void createNewTipButton(){
        createNewTipButton = findViewById(R.id.createNewTipButton);
        createNewTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CreateNewTip.class);
                startActivity(startIntent);
            }
        });
    }

    public void createBackBtn(){
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
        if(InternetConnection.checkConnection(getApplicationContext())){
            final ProgressDialog dialog;

            dialog = new ProgressDialog(TipsActivity.this);
            dialog.setTitle("Getting the tips");
            dialog.setMessage("please wait");
            dialog.show();


            BaseAPIService api = RetrofitClient.getApiService();

            Call<List<Tip>> call = api.getTips();
            //Call<String> call =api.getHelloString();

            //Call<List<Post>> call =api.getPosts();



            call.enqueue(new Callback<List<Tip>>() {
                @Override
                public void onResponse(Call<List<Tip>> call, Response<List<Tip>> response) {
                    dialog.dismiss();
                    if(!response.isSuccessful()){
                        Toast.makeText(TipsActivity.this,"Tipsen har inte laddats"+response.code(),Toast.LENGTH_LONG).show();
                    }

                    List<Tip> allTips = response.body();
                    //Toast.makeText(TipsActivity.this,"Tipsen har laddats "+allTips.get(0).getName(),Toast.LENGTH_LONG).show();


                        System.out.println(allTips.get(1).getTitel());



                }

                @Override
                public void onFailure(Call<List<Tip>> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(TipsActivity.this,"Tipsen har inte laddats2",Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void addTips(Tip tip) {
        LinearLayout layout = findViewById(R.id.newsLinearLayout);

        TipsItemView tipsItemView = new TipsItemView(this, tip);

        tipsItemView.setOnClickListener(l -> {
            if (tipsItemView.getBackground() == null)
                tipsItemView.setBackgroundColor(Color.CYAN);
            else
                tipsItemView.setBackground(null);
        });

        layout.addView(tipsItemView);
    }
}

