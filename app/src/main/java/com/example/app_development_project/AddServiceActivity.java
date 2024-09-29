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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddServiceActivity extends AppCompatActivity {

    private Spinner spinnerCarName;
    private EditText editTextCustomerName, editTextBookingDate, editTextMobileNumber;
    private Button buttonSubmit;

    private DatabaseReference carDatabaseReference; // Firebase reference for fetching cars
    private DatabaseReference serviceDatabaseReference; // Firebase reference for storing service data
    private ArrayList<String> carNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        // Initialize views
        spinnerCarName = findViewById(R.id.editTextCarName);
        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextBookingDate = findViewById(R.id.editTextBookingDate);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Firebase database references
        carDatabaseReference = FirebaseDatabase.getInstance().getReference("cars");
        serviceDatabaseReference = FirebaseDatabase.getInstance().getReference("serviceCars");

        // Fetch car names for spinner
        fetchCarNamesFromFirebase();

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
                submitServiceDetails();
            }
        });
    }

    private void fetchCarNamesFromFirebase() {
        carDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carNameList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String carName = snapshot.child("name").getValue(String.class);
                    if (carName != null) {
                        carNameList.add(carName);
                    }
                }
                // Set the adapter for spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddServiceActivity.this,
                        android.R.layout.simple_spinner_item, carNameList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCarName.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddServiceActivity.this, "Failed to load car names", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String formattedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        editTextBookingDate.setText(formattedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void submitServiceDetails() {
        String carName = spinnerCarName.getSelectedItem().toString();
        String customerName = editTextCustomerName.getText().toString().trim();
        String bookingDate = editTextBookingDate.getText().toString().trim();
        String mobileNumber = editTextMobileNumber.getText().toString().trim();

        if (carName.isEmpty() || customerName.isEmpty() || bookingDate.isEmpty() || mobileNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Calculate ETA (Booking Date + 15 days)
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                calendar.setTime(sdf.parse(bookingDate));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 15); // Add 15 days for ETA
            String etaDate = sdf.format(calendar.getTime());

            // Create a map to store service details in Firebase
            Map<String, Object> serviceDetails = new HashMap<>();
            serviceDetails.put("carName", carName);
            serviceDetails.put("customerName", customerName);
            serviceDetails.put("bookingDate", bookingDate);
            serviceDetails.put("mobileNumber", mobileNumber);
            serviceDetails.put("eta", etaDate); // Store ETA

            // Push the service details to Firebase
            serviceDatabaseReference.push().setValue(serviceDetails).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddServiceActivity.this, "Service added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    Toast.makeText(AddServiceActivity.this, "Failed to add service", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
