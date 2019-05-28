package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.CategoryActivity;
import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.InternetConnection;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewTip extends AppCompatActivity {

    private String title;
    private String descritption;
    private ImageButton saveButton;
    private String[] categoryList;
    private EditText tipTitelEditText;
    private EditText tipDescriptionEditText;
    private boolean[] catChecked;
    private ImageButton deleteButton;
    private TextView deleteTextView;
    private String source;
    private int id;
    private Tip oldTip;

    CheckBox waterCheckBox;
    CheckBox shelterCheckBox;
    CheckBox warmthCheckBox;
    CheckBox foodCheckBox;

    CheckBox healthCheckBox;
    CheckBox securityCheckBox;
    CheckBox otherCheckBox;
    CheckBox storageCheckBox;


    //FACEBOOK ID
    //private String creator = AccessToken.getCurrentAccessToken().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tip);

        categoryList = getResources().getStringArray(R.array.categoryArray);
        source = (String) getIntent().getExtras().get("source");
        catChecked = new boolean[8];


        if (source.equals("MT")) {
            oldTip = (Tip) getIntent().getSerializableExtra("theTip");
            checkCategoryOnOldTip();
        }
        createTipTitle();
        createDescription();
        createSaveButton();
        createDeleteButton();

    }


    public void createTipTitle() {
        tipTitelEditText = findViewById(R.id.tipTitelEdittext);
        if (source.equals("MT")) {
            tipTitelEditText.setText(oldTip.getTitle());
        }

    }

    public void createDescription() {
        tipDescriptionEditText = findViewById(R.id.tipDescriptionEdittext);
        if (source.equals("MT")) {
            tipDescriptionEditText.setText(oldTip.getDescription());
        }
    }

    public void createSaveButton() {
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkIsSelected = checkSelectionExists();
                if (checkIsSelected == false) {
                    Toast.makeText(CreateNewTip.this, "Du måste välja minst en kategori", Toast.LENGTH_SHORT).show();
                } else {
                    title = tipTitelEditText.getText().toString();
                    descritption = tipDescriptionEditText.getText().toString();
                    checkChosenCategory();

                    if (!(source.equals("MT"))) {
                        addATip();
                    } else {
                        updateTip();
                    }

                }


            }

        });


    }

    public void addATip() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;

            dialog = new ProgressDialog(CreateNewTip.this);
            dialog.setTitle("Saving the oldTip");
            dialog.setMessage("please wait");
            dialog.show();

            //Creating object for our interface
            BaseAPIService api = RetrofitClient.getApiService();

            //Defining the method insertuser of our interface

            Tip tip = new Tip(title, catChecked[0], catChecked[1], catChecked[2], catChecked[3], catChecked[4],
                    catChecked[5], catChecked[6], catChecked[7], descritption, 0, "Elsa");
            Call<Tip> call = api.addTip(tip);

            //Creating an anonymous callback
            call.enqueue(new Callback<Tip>() {
                @Override
                public void onResponse(Call<Tip> call, Response<Tip> response) {
                    dialog.dismiss();
                    if (!response.isSuccessful()) {
                        Toast.makeText(CreateNewTip.this, "Tipsen har inte laddats" + response.code(), Toast.LENGTH_LONG).show();
                    }

                    //Displaying the output as a toast
                    Toast.makeText(CreateNewTip.this, "Success", Toast.LENGTH_LONG).show();

                    //GÅ TILL ManipulateTip
                    Intent startIntent = new Intent(getApplicationContext(), ManipulateTip.class);
                    startIntent.putExtra("theTip", tip);
                    startActivity(startIntent);
                }


                @Override
                public void onFailure(Call<Tip> call, Throwable t) {
                    //If any error occured displaying the error as toast
                    Toast.makeText(CreateNewTip.this, "Success", Toast.LENGTH_LONG).show();
                    Intent startIntent = new Intent(getApplicationContext(), ManipulateTip.class);
                    startIntent.putExtra("theTip", tip);
                    startActivity(startIntent);
                    dialog.dismiss();


                }

            });

        }
    }

    public void updateTip() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;

            dialog = new ProgressDialog(CreateNewTip.this);
            dialog.setTitle("Saving the oldTip");
            dialog.setMessage("please wait");
            dialog.show();

            //Creating object for our interface
            BaseAPIService api = RetrofitClient.getApiService();

            //Defining the method insertuser of our interface

            Tip tip = new Tip(title, catChecked[0], catChecked[1], catChecked[2], catChecked[3], catChecked[4],
                    catChecked[5], catChecked[6], catChecked[7], descritption, 0, "Elsa");
            tip.setId(id);
            Call<Tip> call = api.updateTip(tip);

            //Creating an anonymous callback
            call.enqueue(new Callback<Tip>() {
                @Override
                public void onResponse(Call<Tip> call, Response<Tip> response) {
                    dialog.dismiss();
                    if (!response.isSuccessful()) {
                        Toast.makeText(CreateNewTip.this, "Tipset har inte uppdatterats1" + response.code(), Toast.LENGTH_LONG).show();
                    }

                    //Displaying the output as a toast
                    Toast.makeText(CreateNewTip.this, "Tipset har laddats", Toast.LENGTH_LONG).show();

                    //GÅ TILL CATEGORY
                    Intent startIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(startIntent);
                }


                @Override
                public void onFailure(Call<Tip> call, Throwable t) {
                    //If any error occured displaying the error as toast
                    Toast.makeText(CreateNewTip.this, "Tipset har inte uppdatterats2", Toast.LENGTH_LONG).show();
                    Intent startIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(startIntent);
                    dialog.dismiss();
                }

            });

        }
    }

    private void createDeleteButton() {
        deleteButton = findViewById(R.id.deleteTipButton);
        deleteButton.setVisibility(View.INVISIBLE);
        deleteTextView = findViewById(R.id.deleteTextView);
        deleteTextView.setVisibility(View.INVISIBLE);

        if (source.equals("MT")) {
            deleteTextView.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        final ProgressDialog dialog;

                        dialog = new ProgressDialog(CreateNewTip.this);
                        dialog.setTitle("Removing the oldTip");
                        dialog.setMessage("please wait");
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
                                    Toast.makeText(CreateNewTip.this, "Tipset har inte raderats1" + response.code(), Toast.LENGTH_LONG).show();
                                }
                                //Displaying the output as a toast
                                Toast.makeText(CreateNewTip.this, "Tipset har raderats1", Toast.LENGTH_LONG).show();

                                //GÅ TILL CATEGORY
                                Intent startIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                                startActivity(startIntent);
                            }

                            @Override
                            public void onFailure(Call<Tip> call, Throwable t) {
                                //If any error occured displaying the error as toast
                                dialog.dismiss();
                                Toast.makeText(CreateNewTip.this, "Tipset inte raderats 2", Toast.LENGTH_LONG).show();
                                Intent startIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                                startActivity(startIntent);

                            }
                        });
                    }
                }
            });
        }
    }

    private void checkCategoryOnOldTip() {
        warmthCheckBox = (CheckBox) findViewById(R.id.warmthCheckBox);
        waterCheckBox = (CheckBox) findViewById(R.id.waterCheckBox);
        healthCheckBox = (CheckBox) findViewById(R.id.healthCheckBox);
        shelterCheckBox = (CheckBox) findViewById(R.id.shelterCheckBox);

        foodCheckBox = (CheckBox) findViewById(R.id.foodCheckBox);
        securityCheckBox = (CheckBox) findViewById(R.id.securityCheckBox);
        storageCheckBox = (CheckBox) findViewById(R.id.storageCheckBox);
        otherCheckBox = (CheckBox) findViewById(R.id.otherCheckBox);


        if (oldTip.isWarmth()) {
            warmthCheckBox.setChecked(true);
            catChecked[0] = true;
        } else {
            catChecked[0] = false;
        }
        if (oldTip.isWater()) {
            waterCheckBox.setChecked(true);
            catChecked[1] = true;
        } else {
            catChecked[1] = false;
        }
        if (oldTip.isShelter()) {
            shelterCheckBox.setChecked(true);
            catChecked[2] = true;
        } else {
            catChecked[2] = false;
        }
        if (oldTip.isFood()) {
            foodCheckBox.setChecked(true);
            catChecked[3] = true;
        } else {
            catChecked[3] = false;
        }
        if (oldTip.isHealth()) {
            healthCheckBox.setChecked(true);
            catChecked[4] = true;
        } else {
            catChecked[4] = false;
        }
        if (oldTip.isSecurity()) {
            securityCheckBox.setChecked(true);
            catChecked[5] = true;
        } else {
            catChecked[5] = false;
        }
        if (oldTip.isStorage()) {
            storageCheckBox.setChecked(true);
            catChecked[6] = true;
        } else {
            catChecked[6] = false;
        }
        if (oldTip.isOther()) {
            otherCheckBox.setChecked(true);
            catChecked[7] = true;
        } else {
            catChecked[7] = false;
        }
    }

    private void checkChosenCategory() {

        if ((boolean) ((CheckBox) findViewById(R.id.warmthCheckBox)).isChecked() == true) {
            catChecked[0] = true;
        } else {
            catChecked[0] = false;
        }
        if ((boolean) ((CheckBox) findViewById(R.id.waterCheckBox)).isChecked()) {
            catChecked[1] = true;
        } else {
            catChecked[1] = false;
        }
        if ((boolean) ((CheckBox) findViewById(R.id.shelterCheckBox)).isChecked()) {
            catChecked[2] = true;
        } else {
            catChecked[2] = false;
        }
        if ((boolean) ((CheckBox) findViewById(R.id.foodCheckBox)).isChecked()) {
            catChecked[3] = true;
        } else {
            catChecked[3] = false;
        }
        if ((boolean) ((CheckBox) findViewById(R.id.healthCheckBox)).isChecked()) {
            catChecked[4] = true;
        } else {
            catChecked[4] = false;
        }
        if ((boolean) ((CheckBox) findViewById(R.id.securityCheckBox)).isChecked()) {
            catChecked[5] = true;
        } else {
            catChecked[5] = false;
        }
        if ((boolean) ((CheckBox) findViewById(R.id.storageCheckBox)).isChecked()) {
            catChecked[6] = true;
        } else {
            catChecked[6] = false;
        }
        if ((boolean) ((CheckBox) findViewById(R.id.otherCheckBox)).isChecked()) {
            catChecked[7] = true;
        } else {
            catChecked[7] = false;
        }
    }

    private boolean checkSelectionExists() {
        boolean selectionExists = false;
        selectionExists = (boolean) ((CheckBox) findViewById(R.id.warmthCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.waterCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.foodCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.healthCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.securityCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.storageCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.otherCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.shelterCheckBox)).isChecked() ? true : false;
        return selectionExists;
    }
}