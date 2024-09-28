package com.example.app_development_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.example.carshowroom.CarServiceAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CarServiceFragment extends Fragment {

    private ListView listView;
    private ArrayList<CarServiceModel> carServiceList;
    private CarServiceAdapter adapter;
    private FloatingActionButton floatingActionButton;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_service, container, false);

        listView = view.findViewById(R.id.listView);

        // Populate car service list with dummy data
        carServiceList = new ArrayList<>();
        carServiceList.add(new CarServiceModel("Honda Civic", "John Doe", "2 days",R.drawable.ic_launcher_background, "1234567890", "2024-09-01" ));
        carServiceList.add(new CarServiceModel("Honda Civic", "John Doe", "2 days",R.drawable.ic_launcher_background, "1234567890", "2024-09-01" ));
        carServiceList.add(new CarServiceModel("Honda Civic", "John Doe", "2 days",R.drawable.ic_launcher_background, "1234567890", "2024-09-01" ));
        carServiceList.add(new CarServiceModel("Honda Civic", "John Doe", "2 days",R.drawable.ic_launcher_background, "1234567890", "2024-09-01" ));
        carServiceList.add(new CarServiceModel("Honda Civic", "John Doe", "2 days",R.drawable.ic_launcher_background, "1234567890", "2024-09-01" ));
        carServiceList.add(new CarServiceModel("Honda Civic", "John Doe", "2 days",R.drawable.ic_launcher_background, "1234567890", "2024-09-01" ));
        carServiceList.add(new CarServiceModel("Honda Civic", "John Doe", "2 days",R.drawable.ic_launcher_background, "1234567890", "2024-09-01" ));
        carServiceList.add(new CarServiceModel("Toyota Corolla", "Jane Smith", "3 days", R.drawable.ic_launcher_background,"0987654321", "2024-09-02"));
        carServiceList.add(new CarServiceModel("Ford Mustang", "Mark Johnson", "1 day",  R.drawable.ic_launcher_background,"1122334455", "2024-09-03"));

        // Create and set the adapter
        adapter = new CarServiceAdapter(getContext(), carServiceList);
        listView.setAdapter(adapter);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                // Start AddServiceActivity using startActivity
                Intent intent = new Intent(getActivity(), AddServiceActivity.class);
                startActivity(intent);  // Launch the AddServiceActivity
            }
        });
        return view;
    }
}
