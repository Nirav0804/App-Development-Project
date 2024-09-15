package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_development_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarInventoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarInventoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewCars = view.findViewById(R.id.recyclerViewCars);
        recyclerViewCars.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}