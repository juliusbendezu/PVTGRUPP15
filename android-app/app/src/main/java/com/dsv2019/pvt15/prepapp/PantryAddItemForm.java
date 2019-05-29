package com.dsv2019.pvt15.prepapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.InternetConnection;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;
import com.dsv2019.pvt15.prepapp.models.PantryItem;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantryAddItemForm extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    PantryItem itemToUpdate = null;
    boolean makeUpdate = false;

    TextView header;
    Button saveItemButton;
    Button cancelButton;

    EditText nameInput;
    EditText categoryInput;
    EditText amountInput;
    Button dateInput;
    RadioGroup radioGroup;

    TextView dateTextView;
    String date = "";

    //final String owner = AccessToken.getCurrentAccessToken().toString();
    final String owner = "Julius";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_add_item_form);

        initComponents();

        itemToUpdate = (PantryItem) getIntent().getSerializableExtra(PantryItem.KEY);
        if (itemToUpdate != null) {
            makeUpdate = true;
            fillFields();
        }
    }

    private void initComponents() {

        header = findViewById(R.id.addPantryItemHeader);
        saveItemButton = findViewById(R.id.savePantryItemButton);
        saveItemButton.setOnClickListener(l -> savePantryItem());
        cancelButton = findViewById(R.id.addPantryCancelButton);
        cancelButton.setOnClickListener(l -> cancel());

        nameInput = findViewById(R.id.pantryEditName);
        categoryInput = findViewById(R.id.pantryEditCategory);
        amountInput = findViewById(R.id.pantryEditAmount);
        dateInput = findViewById(R.id.datePickerButton);
        dateInput.setOnClickListener(l -> showDatePickerDialog());

        dateTextView = findViewById(R.id.pantryDateTextView);

        radioGroup = findViewById(R.id.addPantryItemRG);
    }

    private void cancel() {
        //startActivity(new Intent(this, MainActivity.class));
        onBackPressed();
    }

    private void fillFields() {
        nameInput.setText(itemToUpdate.getName());
        categoryInput.setText(itemToUpdate.getCategory());
        amountInput.setText(String.valueOf(itemToUpdate.getAmount()));

        int id = 0;
        switch (itemToUpdate.getGeneralCategory()) {
            case PantryItem.FOOD_CATEGORY:
                id = R.id.foodTypeRadio;
                break;
            case PantryItem.MEDICINE_CATEGORY:
                id = R.id.medicineTypeRadio;
                break;
            case PantryItem.OTHER_CATEGORY:
                id = R.id.otherTypeRadio;
                break;
        }

        radioGroup.check(id);
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Dialog dateDialog = new DatePickerDialog(this, this, year, month, day);
        dateDialog.show();
    }

    private void savePantryItem() {
        PantryItem item = checkFields();
        if (item == null)
            return;

        if (!InternetConnection.checkConnection(this)) {
            showErrorMessage("Ett fel upstod vid anslutning till servern, kontrollera din internetuppkoppling");
            return;
        }

        if (makeUpdate)
            makePutRequest(item);
        else
            makePostRequest(item);
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

        expiryDate = date;
        if (expiryDate.isEmpty()) {
            expiryDate = "-";
        }

        return new PantryItem(name, category, type, expiryDate, amount, owner);
    }

    private void makePostRequest(PantryItem item) {

        BaseAPIService apiService = RetrofitClient.getApiService();
        Call<PantryItem> call = apiService.addPantryItem(item);

        call.enqueue(new Callback<PantryItem>() {
            @Override
            public void onResponse(Call<PantryItem> call, Response<PantryItem> response) {
                if (!response.isSuccessful()) {
                    showErrorMessage("Ett fel uppstod vid anslutning till servern, vänlig försök igen: " + response.message());
                    return;
                }

                Toast.makeText(PantryAddItemForm.this, "Skapat!", Toast.LENGTH_SHORT).show();
                startActivity(PantryFragment.createIntent(PantryAddItemForm.this));
            }

            @Override
            public void onFailure(Call<PantryItem> call, Throwable t) {
                showErrorMessage("Skapat!");
                startActivity(PantryFragment.createIntent(PantryAddItemForm.this));
            }
        });
    }

    private void makePutRequest(PantryItem item) {
        item.setId(itemToUpdate.getId());
        BaseAPIService apiService = RetrofitClient.getApiService();
        Call<PantryItem> call = apiService.updatePantryItem(item);
        call.enqueue(new Callback<PantryItem>() {
            @Override
            public void onResponse(Call<PantryItem> call, Response<PantryItem> response) {
                if (!response.isSuccessful()) {
                    showErrorMessage("Ett fel uppstod vid anslutning till servern, vänlig försök igen: " + response.message());
                    return;
                }

                Toast.makeText(PantryAddItemForm.this, "Sparat!", Toast.LENGTH_SHORT).show();
                startActivity(PantryFragment.createIntent(PantryAddItemForm.this));
            }

            @Override
            public void onFailure(Call<PantryItem> call, Throwable t) {
                showErrorMessage("Sparat!");
                startActivity(PantryFragment.createIntent(PantryAddItemForm.this));
            }
        });
    }

    private void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        date = String.format("%d-%s-%d", year, month < 10 ? "0" + month : month, dayOfMonth);
        dateTextView.setText(date);
    }

}
