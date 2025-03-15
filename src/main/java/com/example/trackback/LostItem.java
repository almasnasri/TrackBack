package com.example.trackback;

public class LostItem {
    private String itemName;
    private String location;
    private String date;

    public LostItem(String itemName, String location, String date) {
        this.itemName = itemName;
        this.location = location;
        this.date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}
