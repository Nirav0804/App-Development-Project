package com.example.carshowroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_development_project.CarServiceModel;
import com.example.app_development_project.R;

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

        // Get current item to be displayed
        CarServiceModel currentCarService = (CarServiceModel) getItem(position);

        // Get references to views
        ImageView carImage = convertView.findViewById(R.id.car_image);
        TextView carName = convertView.findViewById(R.id.car_name);
        TextView customerName = convertView.findViewById(R.id.customer_name);
        TextView eta = convertView.findViewById(R.id.eta);

        // Set data into views
        carImage.setImageResource(currentCarService.getImageResource());
        carName.setText(currentCarService.getCarName());
        customerName.setText(currentCarService.getCustomerName());
        eta.setText("ETA: " + currentCarService.getEta());

        return convertView;
    }
}
