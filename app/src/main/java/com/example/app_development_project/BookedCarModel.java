package com.example.app_development_project;

public class BookedCarModel {
    private String carName;
    private String customerName;
    private String bookingDate;
    private String carImageUrl; // Field to hold the car image URL

    // Constructor
    public BookedCarModel(String carName, String customerName, String bookingDate, String carImageUrl) {
        this.carName = carName;
        this.customerName = customerName;
        this.bookingDate = bookingDate;
        this.carImageUrl = carImageUrl;
    }

    // Getters
    public String getCarName() {
        return carName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getCarImageUrl() {
        return carImageUrl;
    }
}
