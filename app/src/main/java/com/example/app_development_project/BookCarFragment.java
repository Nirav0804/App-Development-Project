package com.example.app_development_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookCarFragment extends Fragment {

    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private ArrayList<BookedCarModel> bookedCarList;
    private BookedCarAdapter bookedCarAdapter;
    private DatabaseReference bookingDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_car, container, false);

        // Initialize ListView and FloatingActionButton
        listView = view.findViewById(R.id.listViewBookedCars);
        floatingActionButton = view.findViewById(R.id.floatingActionButton2); // As per your XML ID

        // Firebase reference for booked cars
        bookingDatabase = FirebaseDatabase.getInstance().getReference("bookedCars");

        // Initialize the list and adapter for ListView
        bookedCarList = new ArrayList<>();
        bookedCarAdapter = new BookedCarAdapter(getContext(), bookedCarList);

        // Set the adapter to the ListView
        listView.setAdapter(bookedCarAdapter);

        // Fetch booked cars from Firebase
        fetchBookedCarsFromFirebase();

        // Handle FloatingActionButton click to open AddBookingActivity
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open AddBookingActivity to add a new booking
                Intent intent = new Intent(getContext(), AddBookingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // Fetch booked cars from Firebase and update the ListView
    private void fetchBookedCarsFromFirebase() {
        bookingDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookedCarList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String carName = snapshot.child("carName").getValue(String.class);
                    String customerName = snapshot.child("customerName").getValue(String.class);
                    String bookingDate = snapshot.child("bookingDate").getValue(String.class);
                    String carImageUrl = snapshot.child("carImageUrl").getValue(String.class); // Fetch image URL

                    BookedCarModel car = new BookedCarModel(carName, customerName, bookingDate, carImageUrl);
                    bookedCarList.add(car);  // Add car object to the list
                }
                // Notify the adapter to update the ListView
                bookedCarAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Toast.makeText(getContext(), "Failed to load booked cars.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
