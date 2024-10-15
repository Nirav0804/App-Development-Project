package com.example.app_development_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddBookingActivity extends AppCompatActivity {

    private Spinner spinnerCarName;
    private EditText editTextCustomerName;
    private Button buttonSubmit;
    private DatabaseReference bookingDatabase;
    private DatabaseReference carsDatabase;

    private ArrayList<String> carNameList = new ArrayList<>();
    private ArrayList<String> carImageUrlList = new ArrayList<>();
    private String selectedCarName;
    private String selectedCarImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        // Initialize views
        spinnerCarName = findViewById(R.id.spinnerCarName);
        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Firebase reference
        bookingDatabase = FirebaseDatabase.getInstance().getReference("bookedCars");
        carsDatabase = FirebaseDatabase.getInstance().getReference("cars");

        // Load car names and images from Firebase into Spinner
        loadCarNamesFromFirebase();

        // Handle Submit button click
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookingToFirebase();
            }
        });

        // Handle car name selection from spinner
        spinnerCarName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCarName = carNameList.get(position);    // Store selected car name
                selectedCarImageUrl = carImageUrlList.get(position);  // Store selected car image URL
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCarName = null;
                selectedCarImageUrl = null;
            }
        });
    }

    // Load car names and image URLs from Firebase
    private void loadCarNamesFromFirebase() {
        carsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                carNameList.clear();
                carImageUrlList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String carName = snapshot.child("name").getValue(String.class);   // Get car name
                    String carImageUrl = snapshot.child("imageURL").getValue(String.class);   // Get car image URL
                    if (carName != null && carImageUrl != null) {
                        carNameList.add(carName);       // Add car name to list
                        carImageUrlList.add(carImageUrl); // Add car image URL to list
                    }
                }
                // Populate Spinner with car names
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBookingActivity.this, android.R.layout.simple_spinner_item, carNameList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCarName.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddBookingActivity.this, "Failed to load car names", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBookingToFirebase() {
        String customerName = editTextCustomerName.getText().toString().trim();

        if (selectedCarName == null || customerName.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current date
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());

        // Create a new booking
        Map<String, Object> bookingDetails = new HashMap<>();
        bookingDetails.put("carName", selectedCarName);
        bookingDetails.put("customerName", customerName);
        bookingDetails.put("bookingDate", currentDate);
        bookingDetails.put("carImageUrl", selectedCarImageUrl); // Store image URL with booking

        // Push booking to Firebase
        bookingDatabase.push().setValue(bookingDetails).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Reduce the car quantity in the "cars" database
                reduceCarQuantity(selectedCarName);
                Toast.makeText(this, "Booking added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add booking.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reduceCarQuantity(String carName) {
        carsDatabase.child(carName).child("quantity").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer quantity = dataSnapshot.getValue(Integer.class);

                // Debugging log
                Log.d("AddBookingActivity", "Quantity for " + carName + ": " + quantity);

                if (quantity != null) {
                    if (quantity > 0) {
                        carsDatabase.child(carName).child("quantity").setValue(quantity - 1);
                    } else {
                        Toast.makeText(AddBookingActivity.this, "No more cars available to book.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddBookingActivity.this, "Quantity is null for " + carName, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
                Toast.makeText(AddBookingActivity.this, "Failed to get quantity from database.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
