package com.example.app_development_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarInventoryFragment extends Fragment {

    private ListView listViewCars;
    private ArrayList<CarModel> carList;
    private CarInventoryAdapter adapter;
    private DatabaseReference carsDatabaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_inventory, container, false);

        listViewCars = view.findViewById(R.id.listViewCars);
        carList = new ArrayList<>();
        adapter = new CarInventoryAdapter(getContext(), carList);
        listViewCars.setAdapter(adapter);

        // Initialize Firebase Database Reference
        carsDatabaseRef = FirebaseDatabase.getInstance().getReference("cars");

        // Fetch car data from Firebase
        fetchCarData();

        return view;
    }

    private void fetchCarData() {
        carsDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carList.clear(); // Clear existing data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String imageURL = snapshot.child("imageURL").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    double price = snapshot.child("price").getValue(Double.class);
                    int quantity = snapshot.child("quantity").getValue(Integer.class);
                    int year = snapshot.child("year").getValue(Integer.class);

                    CarModel car = new CarModel(imageURL, name, price, quantity, year);
                    carList.add(car);
                }
                adapter.notifyDataSetChanged(); // Notify adapter of data changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load cars: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
