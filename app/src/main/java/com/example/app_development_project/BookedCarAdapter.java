package com.example.app_development_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide; // Import Glide for image loading

import java.util.ArrayList;

public class BookedCarAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BookedCarModel> bookedCarList;

    public BookedCarAdapter(Context context, ArrayList<BookedCarModel> bookedCarList) {
        this.context = context;
        this.bookedCarList = bookedCarList;
    }

    @Override
    public int getCount() {
        return bookedCarList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookedCarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_booked_car, parent, false);
            holder = new ViewHolder();
            holder.carName = convertView.findViewById(R.id.car_name);
            holder.customerName = convertView.findViewById(R.id.customer_name);
            holder.bookingDate = convertView.findViewById(R.id.booking_date);
            holder.carImage = convertView.findViewById(R.id.car_image); // Add ImageView for the car image
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get current car details
        BookedCarModel car = (BookedCarModel) getItem(position);

        // Set the values to the views
        holder.carName.setText("Car: " + car.getCarName());
        holder.customerName.setText("Customer: " + car.getCustomerName());
        holder.bookingDate.setText("Date: " + car.getBookingDate());

        // Load the car image using Glide
        Glide.with(context)
                .load(car.getCarImageUrl()) // Load image from URL
                .into(holder.carImage); // Set it to the ImageView

        return convertView;
    }

    // ViewHolder pattern for better performance
    private static class ViewHolder {
        TextView carName, customerName, bookingDate;
        ImageView carImage; // ImageView for car image
    }
}
