package com.example.app_development_project;

public class CarModel {
    private String imageURL;
    private String name;
    private double price;
    private int quantity;
    private int year;

    // Constructor
    public CarModel() {}

    public CarModel(String imageURL, String name, double price, int quantity, int year) {
        this.imageURL = imageURL;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.year = year;
    }

    // Getters
    public String getImageUrl() { return imageURL; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getYear() { return year; }
}
