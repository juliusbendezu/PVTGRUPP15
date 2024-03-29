package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    int id;
    Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_tip);

        oldTip = (Tip) getIntent().getSerializableExtra("theTip");
        likes = oldTip.getLikes();
        id = oldTip.getId();

        setTipTitle();
        setCategorys();
        setDescription();
        setEditButton();
        setLikeButton();
        createDeleteButton();
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
        allCategorys += System.getProperty("line.separator") + "Creator: " + oldTip.getCreator();
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
                    likeButton.setImageResource(R.drawable.like);
                    likes++;
                    setCategorys();
                    changeisLiked(true);

                } else {
                    likeButton.setImageResource(R.drawable.liked);
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
          id = oldTip.getId();
          oldTip.setId(id);
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
    private void createDeleteButton() {
        deleteButton = findViewById(R.id.deleteTipButton);
        deleteButton.setVisibility(View.INVISIBLE);

        if (oldTip.getCreator().equals(MainActivity.CREATOR_NAME)) {
                deleteButton.setVisibility(View.VISIBLE);

                deleteButton.setOnClickListener(this::showWarningPopup);
            }
    }

    private void showWarningPopup(View v) {
        LayoutInflater inflater = getLayoutInflater();
        View popup = inflater.inflate(R.layout.popup_warning, null);
        PopupWindow warning = new PopupWindow(popup, (int) (getWindowManager().getDefaultDisplay().getWidth() * .8),
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        TextView message = popup.findViewById(R.id.popupWarningMessageTV);
        message.setText("Är du säker på att du vill radera detta tips?\n(Detta går inte att ångra)");
        Button cancel = popup.findViewById(R.id.popupWarningCancelButton);
        cancel.setText("Avbryt");
        cancel.setOnClickListener(l -> warning.dismiss());
        Button ok = popup.findViewById(R.id.popupWarningOkButton);
        ok.setText("Ta bort");
        ok.setOnClickListener(l -> makeDeleteRequest());
        warning.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    private void makeDeleteRequest() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;

            dialog = new ProgressDialog(ManipulateTip.this);
            dialog.setTitle("Raderar tipset");
            dialog.setMessage("var god vänta");
            dialog.show();

            //oldTip = (Tip) getIntent().getSerializableExtra("theTip");
            id = (int) oldTip.getId();
            BaseAPIService api = RetrofitClient.getApiService();
            Call<Tip> call = api.deleteTip(id);

            call.enqueue(new Callback<Tip>() {
                @Override
                public void onResponse(Call<Tip> call, Response<Tip> response) {
                    dialog.dismiss();
                    if (!response.isSuccessful()) {
                        Toast.makeText(ManipulateTip.this, "Tipset har inte raderats, försök igen", Toast.LENGTH_LONG).show();
                    }
                    //Displaying the output as a toast
                    Toast.makeText(ManipulateTip.this, "Tipset har raderats", Toast.LENGTH_LONG).show();

                    //GÅ TILL CATEGORY
                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startIntent.putExtra("source", "fromTip");
                    startActivity(startIntent);
                }

                @Override
                public void onFailure(Call<Tip> call, Throwable t) {
                    //If any error occured displaying the error as toast
                    dialog.dismiss();
                    Toast.makeText(ManipulateTip.this, "Tipset har raderats", Toast.LENGTH_LONG).show();
                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startIntent.putExtra("source", "fromTip");
                    startActivity(startIntent);
                }
            });
        }
    }

}
