package com.example.app_development_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookedCarAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BookedCarModel> bookedCarList;
    private DatabaseReference bookingDatabase;

    public BookedCarAdapter(Context context, ArrayList<BookedCarModel> bookedCarList) {
        this.context = context;
        this.bookedCarList = bookedCarList;
        bookingDatabase = FirebaseDatabase.getInstance().getReference("bookedCars"); // Initialize Firebase reference
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_booked_car, parent, false);
            holder = new ViewHolder();
            holder.carName = convertView.findViewById(R.id.car_name);
            holder.customerName = convertView.findViewById(R.id.customer_name);
            holder.bookingDate = convertView.findViewById(R.id.booking_date);
            holder.carImage = convertView.findViewById(R.id.car_image);
            holder.btnCarDelivered = convertView.findViewById(R.id.btn_car_delivered); // Add button
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get current car details
        final BookedCarModel car = (BookedCarModel) getItem(position);

        // Set the values to the views
        holder.carName.setText("Car: " + car.getCarName());
        holder.customerName.setText("Customer: " + car.getCustomerName());
        holder.bookingDate.setText("Date: " + car.getBookingDate());

        // Load the car image using Glide
        Glide.with(context)
                .load(car.getCarImageUrl())
                .into(holder.carImage);

        // Handle "Is Car Delivered?" button click
        holder.btnCarDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove car from Firebase using carName, customerName, and bookingDate
                removeCarFromFirebase(car, position);
            }
        });

        return convertView;
    }

    // Method to remove the car from Firebase using carName, customerName, and bookingDate
    private void removeCarFromFirebase(final BookedCarModel car, final int position) {
        // Create a query to find the booking that matches the car details
        Query query = bookingDatabase.orderByChild("carName").equalTo(car.getCarName());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean carFound = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Check if the customerName and bookingDate match as well
                    String customerName = snapshot.child("customerName").getValue(String.class);
                    String bookingDate = snapshot.child("bookingDate").getValue(String.class);

                    if (customerName != null && bookingDate != null &&
                            customerName.equals(car.getCustomerName()) &&
                            bookingDate.equals(car.getBookingDate())) {
                        // If all fields match, remove the entry
                        snapshot.getRef().removeValue();
                        carFound = true;
                        bookedCarList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Car marked as delivered", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (!carFound) {
                    Toast.makeText(context, "Car booking not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Failed to remove car: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ViewHolder pattern for better performance
    private static class ViewHolder {
        TextView carName, customerName, bookingDate;
        ImageView carImage;
        Button btnCarDelivered; // Add button to the ViewHolder
    }
}
