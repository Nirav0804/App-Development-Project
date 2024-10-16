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

public class CarServiceFragment extends Fragment implements CarServiceAdapter.OnCarDeliveredListener {

    private ListView listView;
    private ArrayList<CarServiceModel> carServiceList;
    private CarServiceAdapter adapter;
    private FloatingActionButton floatingActionButton;

    // Firebase reference
    private DatabaseReference serviceCarsDatabaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_service, container, false);

        listView = view.findViewById(R.id.listView);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);

        // Firebase reference
        serviceCarsDatabaseRef = FirebaseDatabase.getInstance().getReference("serviceCars");

        // Initialize the list and adapter
        carServiceList = new ArrayList<>();
        adapter = new CarServiceAdapter(getContext(), carServiceList, this); // Pass 'this' as the listener
        listView.setAdapter(adapter);

        // Fetch the car services from Firebase
        fetchServiceCarsFromFirebase();

        // FloatingActionButton click listener to open AddServiceActivity
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddServiceActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchServiceCarsFromFirebase() {
        // Attach a ValueEventListener to the "serviceCars" reference
        serviceCarsDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carServiceList.clear();  // Clear the list before adding new data

                // Loop through all service cars in Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String carName = snapshot.child("carName").getValue(String.class);
                    String customerName = snapshot.child("customerName").getValue(String.class);
                    String eta = snapshot.child("eta").getValue(String.class);
                    String mobileNumber = snapshot.child("mobileNumber").getValue(String.class);
                    String bookingDate = snapshot.child("bookingDate").getValue(String.class);
                    String imageURL = snapshot.child("imageURL").getValue(String.class); // Fetch imageURL

                    if (carName != null && customerName != null && eta != null && mobileNumber != null && bookingDate != null && imageURL != null) {
                        CarServiceModel carService = new CarServiceModel(carName, customerName, eta, mobileNumber, bookingDate, imageURL);
                        carServiceList.add(0, carService);
                    }
                }

                // Notify the adapter that the data has changed, so the list can be updated
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(getContext(), "Failed to load car services", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Implement the method from the listener
    @Override
    public void onCarDelivered(CarServiceModel carService) {
        removeCarService(carService); // Call method to remove the car service
    }

    private void removeCarService(CarServiceModel carService) {
        // Remove the car service from Firebase
        serviceCarsDatabaseRef.orderByChild("carName").equalTo(carService.getCarName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue(); // Remove car service from Firebase
                }
                // Refresh the list after removal
                carServiceList.remove(carService); // Remove from local list
                adapter.notifyDataSetChanged(); // Notify adapter to refresh the list
                Toast.makeText(getContext(), "Car service marked as delivered.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to remove car service.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
