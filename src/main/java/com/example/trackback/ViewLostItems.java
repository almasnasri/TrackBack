package com.example.trackback;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ViewLostItems {
    @FXML private ImageView ImageView1, ImageView2, ImageView3, ImageView4, ImageView5, ImageView6;
    @FXML private Button viewItem1, viewItem2, viewItem3, viewItem4, viewItem5, viewItem6;
    @FXML private Button prevButton, nextButton;

    private List<LostItem> lostItems = new ArrayList<>();
    private int currentIndex = 0;

    // Database connection details (UPDATE THESE)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/reportfounditem";  // Change this
    private static final String DB_USER = "root";  // Change this
    private static final String DB_PASSWORD = "BellaNabila05_";  // Change this

    public void initialize() {
        fetchLostItemsFromDatabase();
        updateUI();
    }

    private void fetchLostItemsFromDatabase() {
        lostItems.clear();
        String sql = "SELECT Name, phone_number, category, location_found, date_found, image_path FROM founditems";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LostItem item = new LostItem(
                        rs.getString("Name"),
                        rs.getString("phone_number"),
                        rs.getString("category"),
                        rs.getString("location_found"),
                        rs.getString("date_found"),
                        rs.getString("image_path")
                );
                lostItems.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI() {
        ImageView[] imageViews = {ImageView1, ImageView2, ImageView3, ImageView4, ImageView5, ImageView6};
        Button[] viewButtons = {viewItem1, viewItem2, viewItem3, viewItem4, viewItem5, viewItem6};

        for (int i = 0; i < 6; i++) {
            if (currentIndex + i < lostItems.size()) {
                LostItem item = lostItems.get(currentIndex + i);
                String imagePath = item.getImagePath();

                if (imagePath != null && !imagePath.isEmpty()) {
                    imageViews[i].setImage(new Image("file:" + imagePath));  // Load from local path
                } else {
                    imageViews[i].setImage(null);
                }

                int index = currentIndex + i;
                viewButtons[i].setOnAction(e -> showItemDetails(lostItems.get(index)));
            } else {
                imageViews[i].setImage(null);
                viewButtons[i].setOnAction(null);
            }
        }

        prevButton.setDisable(currentIndex == 0);
        nextButton.setDisable(currentIndex + 6 >= lostItems.size());
    }

    private void showItemDetails(LostItem item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lost Item Details");
        alert.setHeaderText("Please Contact:");
        alert.setContentText("Name: " + item.getName() +
                "\nPhone: " + item.getPhone() +
                "\n\nItem Details:" +
                "\nCategory: " + item.getCategory() +
                "\nLocation Found: " + item.getLocationFound() +
                "\nDate Found: " + item.getDateFound());
        alert.showAndWait();
    }

    @FXML
    private void handleNext() {
        if (currentIndex + 6 < lostItems.size()) {
            currentIndex += 6;
            updateUI();
        }
    }

    @FXML
    private void handlePrev() {
        if (currentIndex > 0) {
            currentIndex -= 6;
            updateUI();
        }
    }
}

