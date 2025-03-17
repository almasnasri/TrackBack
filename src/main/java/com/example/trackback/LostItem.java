package com.example.trackback;

public class LostItem {
    private String name;
    private String phone;
    private String category;
    private String locationFound;
    private String dateFound;
    private String imagePath;

    // Constructor
    public LostItem(String name, String phone, String category, String locationFound, String dateFound, String imagePath) {
        this.name = name;
        this.phone = phone;
        this.category = category;
        this.locationFound = locationFound;
        this.dateFound = dateFound;
        this.imagePath = imagePath;
    }

    // Getters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getCategory() { return category; }
    public String getLocationFound() { return locationFound; }
    public String getDateFound() { return dateFound; }
    public String getImagePath() { return imagePath; }
}
