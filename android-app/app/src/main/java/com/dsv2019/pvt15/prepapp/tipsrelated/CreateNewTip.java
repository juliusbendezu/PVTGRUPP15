package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.TipsActivity;
import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.InternetConnection;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewTip extends Activity {

    private String title;
    private String descritption;
    private ImageButton saveButton;
    private String[] categoryList;
    private EditText tipTitelEditText;
    private EditText tipDescriptionEditText;
    private boolean[] catChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tip);

        categoryList = getResources().getStringArray(R.array.categoryArray);

        catChecked = new boolean[8];
        createTipTitle();
        createDescription();
        createSaveButton();
    }

    public void createTipTitle() {
        tipTitelEditText = findViewById(R.id.tipTitelEdittext);
        title = tipTitelEditText.getText().toString();
    }

    public void createDescription() {
        tipDescriptionEditText = findViewById(R.id.tipDescriptionEdittext);
        descritption = tipDescriptionEditText.getText().toString();
    }

    //kolla retrofit
    public void createSaveButton(){
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkIsSelected = checkSelectionExists();
                if (checkIsSelected == false){
                    Toast.makeText(CreateNewTip.this, "Du måste välja minst en kategori", Toast.LENGTH_SHORT).show();
                }
                addATip();

            }
        });
    }

    public void addATip() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;

            dialog = new ProgressDialog(CreateNewTip.this);
            dialog.setTitle("Saving the tip");
            dialog.setMessage("please wait");
            dialog.show();


            BaseAPIService api = RetrofitClient.getApiService();

            Call<Tip> call = api.addTip(title,descritption,catChecked[0],catChecked[1],catChecked[2],catChecked[3],catChecked[4],catChecked[5],catChecked[6],catChecked[7],0,"Elsa");

            call.enqueue(new Callback<Tip>() {
                @Override
                public void onResponse(Call<Tip> call, Response<Tip> response) {
                    dialog.dismiss();
                    if (!response.isSuccessful()) {
                        Toast.makeText(CreateNewTip.this, "Tipset har inte sparats " + response.code(), Toast.LENGTH_LONG).show();
                        call.clone().enqueue(this);
                    }
                    Toast.makeText(CreateNewTip.this, "Tipset har sparats " , Toast.LENGTH_LONG).show();

                    }



                @Override
                public void onFailure(Call<Tip> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(CreateNewTip.this, "Tipset har inte sparats2", Toast.LENGTH_LONG).show();
                    call.clone().enqueue(this);
                }
            });
        }
    }

    private boolean[] checkChosenCategory(){

        if((boolean) ((CheckBox) findViewById(R.id.warmthCheckBox)).isChecked()==true){
            catChecked[0]=true;
        }else {catChecked[0]=false;}
        if((boolean) ((CheckBox) findViewById(R.id.waterCheckBox)).isChecked()){
            catChecked[1]=true;
        }else {catChecked[1]=false;}
        if((boolean) ((CheckBox) findViewById(R.id.shelterCheckBox)).isChecked()){
            catChecked[2]=true;
        }else {catChecked[2]=false;}
        if((boolean) ((CheckBox) findViewById(R.id.foodCheckBox)).isChecked()){
            catChecked[3]=true;
        }else {catChecked[3]=false;}
        if((boolean) ((CheckBox) findViewById(R.id.healthCheckBox)).isChecked()){
            catChecked[4]=true;
        }else {catChecked[4]=false;}
        if((boolean) ((CheckBox) findViewById(R.id.securityCheckBox)).isChecked()){
            catChecked[5]=true;
        }else {catChecked[5]=false;}
        if((boolean) ((CheckBox) findViewById(R.id.storageCheckBox)).isChecked()){
            catChecked[6]=true;
        }else {catChecked[6]=false;}
        if((boolean) ((CheckBox) findViewById(R.id.otherCheckBox)).isChecked()){
            catChecked[7]=true;
        }else {catChecked[7]=false;}
        return catChecked;
    }

    private boolean checkSelectionExists() {
        boolean selectionExists = false;
        selectionExists = (boolean) ((CheckBox) findViewById(R.id.warmthCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.waterCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.foodCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.healthCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.securityCheckBox)).isChecked() ? true : false ||
                (boolean) ((CheckBox) findViewById(R.id.shelterCheckBox)).isChecked() ? true : false;
        return selectionExists;
    }
}