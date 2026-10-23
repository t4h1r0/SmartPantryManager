package com.tahirsallie.smartpantry.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tahirsallie.smartpantry.R;
import com.tahirsallie.smartpantry.db.DatabaseHelper;
import com.tahirsallie.smartpantry.model.PantryItem;

import java.util.Calendar;
import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {

    /** Intent extra key. Absent/-1 means "add a new item". */
    public static final String EXTRA_ITEM_ID = "item_id";

    private DatabaseHelper dbHelper;
    private long itemId = -1;

    private TextInputLayout layoutName, layoutQuantity;
    private TextInputEditText editName, editQuantity, editExpiry;
    private Spinner spinnerUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // back arrow
        }

        dbHelper = new DatabaseHelper(this);

        layoutName = findViewById(R.id.layout_name);
        layoutQuantity = findViewById(R.id.layout_quantity);
        editName = findViewById(R.id.edit_name);
        editQuantity = findViewById(R.id.edit_quantity);
        editExpiry = findViewById(R.id.edit_expiry);
        spinnerUnit = findViewById(R.id.spinner_unit);
        Button buttonSave = findViewById(R.id.button_save);
        Button buttonClearDate = findViewById(R.id.button_clear_date);

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                this, R.array.units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitAdapter);

        // Did the caller pass an item id? If so, we are editing.
        itemId = getIntent().getLongExtra(EXTRA_ITEM_ID, -1);
        if (itemId != -1) {
            setTitle("Edit ingredient");
            populateForm(itemId);
        } else {
            setTitle("Add ingredient");
        }

        editExpiry.setOnClickListener(v -> showDatePicker());
        buttonClearDate.setOnClickListener(v -> editExpiry.setText(""));
        buttonSave.setOnClickListener(v -> saveItem());
    }

    private void populateForm(long id) {
        PantryItem item = dbHelper.getPantryItem(id);
        if (item == null) {                       // deleted in the meantime
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        editName.setText(item.getName());

        double q = item.getQuantity();
        editQuantity.setText(q == Math.floor(q) ? String.valueOf((long) q) : String.valueOf(q));

        String[] units = getResources().getStringArray(R.array.units);
        for (int i = 0; i < units.length; i++) {
            if (units[i].equalsIgnoreCase(item.getUnit())) {
                spinnerUnit.setSelection(i);
                break;
            }
        }
        editExpiry.setText(item.getExpiryDate());
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) ->
                editExpiry.setText(String.format(Locale.US, "%04d-%02d-%02d",
                        year, month + 1, day)),
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    /** Returns true only if every field passes; shows an error on each failing field. */
    private boolean validateInput() {
        boolean valid = true;

        String name = editName.getText() == null ? "" : editName.getText().toString().trim();
        if (name.isEmpty()) {
            layoutName.setError("Please enter an ingredient name");
            valid = false;
        } else {
            layoutName.setError(null);
        }

        String qtyText = editQuantity.getText() == null
                ? "" : editQuantity.getText().toString().trim();
        if (qtyText.isEmpty()) {
            layoutQuantity.setError("Please enter a quantity");
            valid = false;
        } else {
            try {
                double qty = Double.parseDouble(qtyText);
                if (qty <= 0) {
                    layoutQuantity.setError("Quantity must be greater than 0");
                    valid = false;
                } else {
                    layoutQuantity.setError(null);
                }
            } catch (NumberFormatException e) {
                layoutQuantity.setError("Enter a valid number");
                valid = false;
            }
        }
        return valid;
    }

    private void saveItem() {
        if (!validateInput()) return;      // stop here; errors are already shown

        String name = editName.getText().toString().trim();
        double qty = Double.parseDouble(editQuantity.getText().toString().trim());
        String unit = spinnerUnit.getSelectedItem().toString();
        String expiry = editExpiry.getText() == null
                ? "" : editExpiry.getText().toString().trim();

        PantryItem item = new PantryItem(itemId, name, qty, unit, expiry);

        if (itemId == -1) {
            dbHelper.addPantryItem(item);
            Toast.makeText(this, name + " added", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.updatePantryItem(item);
            Toast.makeText(this, name + " updated", Toast.LENGTH_SHORT).show();
        }
        finish();                           // back to the list, which reloads in onResume
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
