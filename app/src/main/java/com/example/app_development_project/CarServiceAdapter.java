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

public class CarServiceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CarServiceModel> carServiceList;

    public CarServiceAdapter(Context context, ArrayList<CarServiceModel> carServiceList) {
        this.context = context;
        this.carServiceList = carServiceList;
    }

    @Override
    public int getCount() {
        return carServiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return carServiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_car_service, parent, false);
        }

        // Get current car service item
        CarServiceModel currentCarService = (CarServiceModel) getItem(position);

        // Initialize views in the item layout
        TextView carName = convertView.findViewById(R.id.car_name);
        TextView customerName = convertView.findViewById(R.id.customer_name);
        TextView eta = convertView.findViewById(R.id.eta);
        TextView mobileNumber = convertView.findViewById(R.id.mobile_number);
        TextView bookingDate = convertView.findViewById(R.id.booking_date);
        ImageView carImage = convertView.findViewById(R.id.car_image);

        // Bind data to views
        carName.setText(currentCarService.getCarName());
        customerName.setText(currentCarService.getCustomerName());
        eta.setText("ETA: " + currentCarService.getEta());
        mobileNumber.setText("Mobile: " + currentCarService.getMobileNumber());
        bookingDate.setText("Booking Date: " + currentCarService.getBookingDate());

        // Load car image using Glide
        Glide.with(context)
                .load(currentCarService.getImageURL())
                .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                .into(carImage);

        return convertView;
    }
}
