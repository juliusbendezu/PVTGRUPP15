package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dsv2019.pvt15.prepapp.models.PantryItem;

import java.util.InputMismatchException;

public class PantryAddItemForm extends Activity {
    TextView header;
    Button saveItemButton;

    EditText nameInput;
    EditText categoryInput;
    EditText amountInput;
    EditText dateInput;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_add_item_form);

        initComponents();


    }

    private void initComponents() {

        header = findViewById(R.id.addPantryItemHeader);
        saveItemButton = findViewById(R.id.addPantryItemButton);
        saveItemButton.setOnClickListener(l -> savePantryItem());

        nameInput = findViewById(R.id.pantryEditName);
        categoryInput = findViewById(R.id.pantryEditCategory);
        amountInput = findViewById(R.id.pantryEditAmount);
        dateInput = findViewById(R.id.pantryEditDate);

        radioGroup = findViewById(R.id.addPantryItemRG);
    }

    private void savePantryItem() {
        checkFields();
        //Make post request to api
    }

    private void checkFields() {
        String type;
        String name;
        String category;
        int amount;
        String expiryDate;

        int selected = radioGroup.getCheckedRadioButtonId();
        switch (selected) {
            case R.id.foodTypeRadio:
                type = PantryItem.FOOD_CATEGORY;
                break;
            case R.id.medicineTypeRadio:
                type = PantryItem.MEDICINE_CATEGORY;
                break;
            case R.id.otherTypeRadio:
                type = PantryItem.OTHER_CATEGORY;
                break;
            default:
                //Not selected, show errormessage
                return;
        }

        name = nameInput.getText().toString();
        if(name.isEmpty()){
            //doSomething
            return;
        }

        category = categoryInput.getText().toString();
        if(category.isEmpty()){
            //doSomething
            return;
        }


        try{
            String stringAmount = amountInput.getText().toString();
            amount = Integer.parseInt(stringAmount);
        }catch(InputMismatchException e){
            //Errormessage
            return;
        }


        expiryDate = dateInput.getText().toString();
        if(expiryDate.isEmpty()){
            expiryDate = "-";
        }

        PantryItem item = new PantryItem(name, category, expiryDate, type, amount);

    }
}
