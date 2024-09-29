package com.example.app_development_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CarInventoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CarModel> carList;

    public CarInventoryAdapter(Context context, ArrayList<CarModel> carList) {
        this.context = context;
        this.carList = carList;
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int position) {
        return carList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.car_item_layout, parent, false);
        }

        ImageView carImage = convertView.findViewById(R.id.carImage);
        TextView carName = convertView.findViewById(R.id.carName);
        TextView carPrice = convertView.findViewById(R.id.carPrice);
        TextView carYear = convertView.findViewById(R.id.carYear);
        TextView carQuantity = convertView.findViewById(R.id.carQuantity);

        CarModel car = carList.get(position);

        carName.setText(car.getName());
        carPrice.setText("Price: ₹" + car.getPrice());
        carYear.setText("Year: " + car.getYear());
        carQuantity.setText("Quantity: " + car.getQuantity());

        // Load the car image using Glide
        Glide.with(context).load(car.getImageUrl()).into(carImage);

        return convertView;
    }
}
