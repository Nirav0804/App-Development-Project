package com.example.app_development_project;

public class CarServiceModel {
    private String carName;
    private String customerName;
    private String eta;
    private int imageResource;  // For the car's image resource ID
    private String mobileNumber;  // New field for customer's mobile number
    private String bookingDate;   // New field for the booking date

    // Constructor
    public CarServiceModel(String carName, String customerName, String eta, int imageResource, String mobileNumber, String bookingDate) {
        this.carName = carName;
        this.customerName = customerName;
        this.eta = eta;
        this.imageResource = imageResource;
        this.mobileNumber = mobileNumber;
        this.bookingDate = bookingDate;
    }

    // Getter for car name
    public String getCarName() {
        return carName;
    }

    // Getter for customer name
    public String getCustomerName() {
        return customerName;
    }

    // Getter for ETA (Estimated Time of Arrival)
    public String getEta() {
        return eta;
    }

    // Getter for image resource (car image)
    public int getImageResource() {
        return imageResource;
    }

    // Getter for mobile number
    public String getMobileNumber() {
        return mobileNumber;
    }

    // Getter for booking date
    public String getBookingDate() {
        return bookingDate;
    }
}
