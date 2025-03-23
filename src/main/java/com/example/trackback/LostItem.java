/*package com.example.trackback;

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
*/

package com.example.trackback;

// Interface to enforce method implementation
interface ItemDetails {
    String getItemSummary();
}

// Java Record for a concise alternative to LostItem
record LostItemRecord(String name, Integer phone, String category, String locationFound, String dateFound, String imagePath) {}

// Abstract class for common properties
abstract class AbstractLostItem implements ItemDetails {
    protected String name;
    protected Integer phone;
    protected String category;
    protected String locationFound;
    protected String dateFound;
    protected String imagePath;

    public AbstractLostItem(String name, Integer phone, String category, String locationFound, String dateFound, String imagePath) {
        this.name = name;
        this.phone = phone;
        this.category = category;
        this.locationFound = locationFound;
        this.dateFound = dateFound;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public Integer getPhone() { return phone; }
    public String getCategory() { return category; }
    public String getLocationFound() { return locationFound; }
    public String getDateFound() { return dateFound; }
    public String getImagePath() { return imagePath; }

    @Override
    public String getItemSummary() {
        return "**Lost Item**\n" +
                "Name: " + name + "\n" +
                "Phone: " + phone + "\n" +
                "Category: " + category + "\n" +
                "Location Found: " + locationFound + "\n" +
                "Date Found: " + dateFound;
    }
}

// LostItem extends the abstract class
public class LostItem extends AbstractLostItem {

    public LostItem(String name, Integer phone, String category, String locationFound, String dateFound, String imagePath) {
        super(name, phone, category, locationFound, dateFound, imagePath);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LostItem lostItem = (LostItem) obj;
        return name.equals(lostItem.name) &&
                phone.equals(lostItem.phone) &&
                category.equals(lostItem.category) &&
                locationFound.equals(lostItem.locationFound) &&
                dateFound.equals(lostItem.dateFound) &&
                imagePath.equals(lostItem.imagePath);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + phone.hashCode();
    }
}

