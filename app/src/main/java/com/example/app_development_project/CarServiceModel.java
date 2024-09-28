package com.example.app_development_project;

public class CarServiceModel {
    private String carName;
    private String customerName;
    private String eta;
    private int imageResource;

    public CarServiceModel(String carName, String customerName, String eta, int imageResource) {
        this.carName = carName;
        this.customerName = customerName;
        this.eta = eta;
        this.imageResource = imageResource;
    }

    public String getCarName() {
        return carName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEta() {
        return eta;
    }

    public int getImageResource() {
        return imageResource;
    }
}
