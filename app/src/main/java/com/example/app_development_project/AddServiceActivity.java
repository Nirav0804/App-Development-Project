package com.example.app_development_project;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddServiceActivity extends AppCompatActivity {

    private Spinner spinnerCarName; // Spinner for car names
    private EditText editTextCustomerName, editTextBookingDate, editTextMobileNumber;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        // Initialize the views
        spinnerCarName = findViewById(R.id.editTextCarName);
        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextBookingDate = findViewById(R.id.editTextBookingDate);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set up the spinner with car names
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_names_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarName.setAdapter(adapter);

        // Set a DatePicker for Booking Date
        editTextBookingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Handle Submit button click
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extract form data
                String carName = spinnerCarName.getSelectedItem().toString(); // Get selected car name
                String customerName = editTextCustomerName.getText().toString().trim();
                String bookingDate = editTextBookingDate.getText().toString().trim();
                String mobileNumber = editTextMobileNumber.getText().toString().trim();

                // Validate inputs
                if (carName.isEmpty() || customerName.isEmpty() || bookingDate.isEmpty() || mobileNumber.isEmpty()) {
                    Toast.makeText(AddServiceActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle form submission, for now, we just show a success message
                    Toast.makeText(AddServiceActivity.this, "Service added successfully", Toast.LENGTH_SHORT).show();

                    // You can redirect or finish the activity if needed
                    finish();  // Close this activity and return to the previous one
                }
            }
        });
    }

    private void showDatePicker() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog to allow the user to select a date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Format the selected date and set it in the EditText
                        String formattedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        editTextBookingDate.setText(formattedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
