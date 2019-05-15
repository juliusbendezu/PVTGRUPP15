package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.R;

public class CreateNewTip extends Activity implements AdapterView.OnItemSelectedListener{

    private String title;
    private String descritption;
    private String category;
    private ImageButton saveButton;
    private String[] categoryList;
    private EditText tipTitelEditText;
    private EditText tipDescriptionEditText;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tip);

        categoryList = getResources().getStringArray(R.array.categoryArray);

        createTipTitle();
        createDescription();
        fillCategorySpinner();
    }

    public void createTipTitle() {
        tipTitelEditText = findViewById(R.id.tipTitelEdittext);
        title = tipTitelEditText.getText().toString();
    }

    public void createDescription() {
        tipDescriptionEditText = findViewById(R.id.tipDescriptionEdittext);
        descritption = tipDescriptionEditText.getText().toString();
    }

    public void fillCategorySpinner(CreateNewTip this) {
        categorySpinner = findViewById(R.id.categorySpinner);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList);
        categorySpinner.setOnItemSelectedListener(this);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        categorySpinner.setAdapter(aa);
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), categoryList[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }
}

