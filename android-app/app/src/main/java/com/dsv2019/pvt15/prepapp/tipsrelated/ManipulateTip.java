package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.BaseActivity;
import com.dsv2019.pvt15.prepapp.MainActivity;
import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.InternetConnection;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManipulateTip extends BaseActivity {

    TextView editTextView;
    ImageButton editButton;
    ImageButton likeButton;
    TextView titleText;
    TextView categoryText;
    TextView descriptionText;
    Tip oldTip;
    boolean isLiked;
    int likes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_tip);

        oldTip = (Tip) getIntent().getSerializableExtra("theTip");
        likes = oldTip.getLikes();

        setTipTitle();
        setCategorys();
        setDescription();
        setEditButton();
        setLikeButton();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
        startIntent.putExtra(MainActivity.SOURCE, MainActivity.FROM_TIP);
        startActivity(startIntent);
    }

    private void setTipTitle() {
        titleText = findViewById(R.id.tipsTitleTextView);
        System.out.println(oldTip.getTitle());
        titleText.setText(oldTip.getTitle());
    }

    private void setCategorys() {
        String allCategorys = "Tipsets Kategorisering: " + oldTip.getCategorys().get(0);
        categoryText = findViewById(R.id.categoryTextView);
        System.out.println(oldTip.getCategorys().size());
        if (oldTip.getCategorys().size() > 1) {
            for (int i = 1; i < oldTip.getCategorys().size() - 1; i++) {
                allCategorys += ", " + oldTip.getCategorys().get(i);
            }
            allCategorys += " & " + oldTip.getCategorys().get(oldTip.getCategorys().size() - 1);
        }

        allCategorys += System.getProperty("line.separator") + "Likes: "+likes;
        categoryText.setText(allCategorys);
    }

    private void setDescription() {
        descriptionText = findViewById(R.id.tipDescriptionTextView);
        descriptionText.setText(oldTip.getDescription());
    }

    private void setEditButton() {
        editButton = findViewById(R.id.editImageButton);
        editTextView = findViewById(R.id.tipEditTextView);
        editTextView.setVisibility(View.INVISIBLE);
        editButton.setVisibility(View.INVISIBLE);
        if (oldTip.getCreator().equals(MainActivity.CREATOR_NAME)) {
            editButton.setVisibility(View.VISIBLE);
            editTextView.setVisibility(View.VISIBLE);
        }
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CreateNewTip.class);
                startIntent.putExtra("source", "MT");
                startIntent.putExtra("theTip", oldTip);
                startActivity(startIntent);
            }
        });
    }

    private boolean getIsLiked() {
        return isLiked;
    }

    private void changeisLiked(boolean opinion) {
        isLiked = opinion;

        updateTip(isLiked);
    }

    private void setLikeButton() {
        likeButton = findViewById(R.id.likeImageButton);
        if (isLiked==true) {
            likeButton.setImageResource(R.drawable.liked);}
            likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLiked==false) {
                    likeButton.setImageResource(R.drawable.liked);
                    likes++;
                    setCategorys();
                    changeisLiked(true);

                } else {
                    likeButton.setImageResource(R.drawable.like);
                    likes--;
                    setCategorys();
                    changeisLiked(false);
                }
            }
        });

    }

    private void updateTip(boolean isLiked){
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;

            //Creating object for our interface
            BaseAPIService api = RetrofitClient.getApiService();

            //Defining the method insertuser of our interface
           oldTip.setLikes(isLiked);
            Call<Tip> call = api.updateTip(oldTip);

            //Creating an anonymous callback
            call.enqueue(new Callback<Tip>() {
                @Override
                public void onResponse(Call<Tip> call, Response<Tip> response) {

                    //Displaying the output as a toast
                }
                @Override
                public void onFailure(Call<Tip> call, Throwable t) {
                    //If any error occured displaying the error as toast
                }
            });
        }
    }
}
