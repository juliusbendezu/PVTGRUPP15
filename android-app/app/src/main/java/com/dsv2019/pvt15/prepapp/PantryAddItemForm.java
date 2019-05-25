package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.InternetConnection;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;
import com.dsv2019.pvt15.prepapp.models.PantryItem;
import com.facebook.AccessToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantryAddItemForm extends Activity {
    
    //TODO FIX DATE INPUT HERE AND IN XML


    TextView header;
    Button saveItemButton;

    EditText nameInput;
    EditText categoryInput;
    EditText amountInput;
    EditText dateInput;
    RadioGroup radioGroup;

    //final String owner = AccessToken.getCurrentAccessToken().toString();
    final String owner = "Julius";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_add_item_form);

        initComponents();
    }

    private void initComponents() {

        header = findViewById(R.id.addPantryItemHeader);
        saveItemButton = findViewById(R.id.savePantryItemButton);
        saveItemButton.setOnClickListener(l -> savePantryItem());

        nameInput = findViewById(R.id.pantryEditName);
        categoryInput = findViewById(R.id.pantryEditCategory);
        amountInput = findViewById(R.id.pantryEditAmount);
        dateInput = findViewById(R.id.pantryEditDate);

        radioGroup = findViewById(R.id.addPantryItemRG);
    }

    private void savePantryItem() {
        PantryItem item = checkFields();
        if (item == null)
            return;
        makeRequest(item);
        startActivity(new Intent(this, PantryActivity.class));
    }

    private PantryItem checkFields() {
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
                showErrorMessage("Du måste välja en typ!");
                return null;
        }

        name = nameInput.getText().toString();
        if (name.isEmpty()) {
            showErrorMessage("Namn kan inte vara tomt!");
            return null;
        }

        category = categoryInput.getText().toString();
        if (category.isEmpty()) {
            showErrorMessage("Kategori kan inte vara tomt!");
            return null;
        }

        try {
            String stringAmount = amountInput.getText().toString();
            amount = Integer.parseInt(stringAmount);
        } catch (NumberFormatException e) {
            showErrorMessage("Skriv antal som ett heltal");
            return null;
        }

        expiryDate = dateInput.getText().toString();
        if (expiryDate.isEmpty()) {
            expiryDate = "-";
        }

        return new PantryItem(name, category, type, expiryDate, amount, owner);
    }

    private void makeRequest(PantryItem item) {
        if (!InternetConnection.checkConnection(this)) {
            showErrorMessage("Ett fel upstod vid anslutning till servern, kontrollera din internetuppkoppling");
            return;
        }

        BaseAPIService apiService = RetrofitClient.getApiService();
        Call<PantryItem> call = apiService.addPantryItem(item);

        call.enqueue(new Callback<PantryItem>() {
            @Override
            public void onResponse(Call<PantryItem> call, Response<PantryItem> response) {
                if (!response.isSuccessful()) {
                    showErrorMessage("Ett fel uppstod vid anslutning till servern, vänlig försök igen: " + response.message());
                    return;
                }

                Toast.makeText(PantryAddItemForm.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PantryItem> call, Throwable t) {
                showErrorMessage("Error: " + t.getMessage());
            }
        });
    }

    private void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
