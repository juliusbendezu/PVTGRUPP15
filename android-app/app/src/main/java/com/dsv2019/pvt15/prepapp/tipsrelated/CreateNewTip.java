package com.dsv2019.pvt15.prepapp.tipsrelated;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.R;

public class CreateNewTip extends Activity {

    private String title;
    private String descritption;
    private String category;
    private ImageButton saveButton;
    private String[] categoryList;
    private EditText tipTitelEditText;
    private EditText tipDescriptionEditText;
    private Spinner categorySpinner;
    private boolean[] catChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tip);

        categoryList = getResources().getStringArray(R.array.categoryArray);

        catChecked = new boolean[7];
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




            Tip newSP = new Tip(title,descritption,checkChosenCategory(),"Elsa");


//                Intent startIntent = new Intent(getApplicationContext(), CreateNewTip.class);
//                startActivity(startIntent);
            }
        });
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




//Spara denna för preppförrådskod. (orkar inte söka upp det igen sen)
//    public void fillCategorySpinner(CreateNewTip this) {
//        categorySpinner = findViewById(R.id.categorySpinner);
//        //Getting the instance of Spinner and applying OnItemSelectedListener on it
//
//        //Creating the ArrayAdapter instance having the bank name list
//        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList);
//        categorySpinner.setOnItemSelectedListener(this);
//
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        categorySpinner.setAdapter(aa);
//    }

//
//    //Performing action onItemSelected and onNothing selected
//    @Override
//    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(), categoryList[position], Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//// TODO Auto-generated method stub
//
//    }
