package com.example.app_development_project;

public class CarServiceModel {
    private String carName;
    private String customerName;
    private String eta;
    private String mobileNumber;
    private String bookingDate;
    private String imageURL; // Added imageURL

    public CarServiceModel(String carName, String customerName, String eta, String mobileNumber, String bookingDate, String imageURL) {
        this.carName = carName;
        this.customerName = customerName;
        this.eta = eta;
        this.mobileNumber = mobileNumber;
        this.bookingDate = bookingDate;
        this.imageURL = imageURL;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getImageURL() {
        return imageURL; // Getter for imageURL
    }
}
