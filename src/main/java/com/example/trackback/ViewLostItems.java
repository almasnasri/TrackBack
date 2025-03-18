package com.example.trackback;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Optional; // For Optional<ButtonType>
import javafx.application.Platform; // For Platform.exit()
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ViewLostItems {
    @FXML private ImageView ImageView1, ImageView2, ImageView3, ImageView4, ImageView5, ImageView6;
    @FXML private Button viewItem1, viewItem2, viewItem3, viewItem4, viewItem5, viewItem6;
    @FXML public Button prevButton, nextButton;


    @FXML
    private Button buttonLogout;

    @FXML
    private Button buttonReport;


    private List<LostItem> lostItems = new ArrayList<>();
    public int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 6;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/reportfounditem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "BellaNabila05_";

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
            System.out.println("Total items fetched: " + lostItems.size()); // Debugging
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI() {
        System.out.println("Updating UI... Current Page: " + currentPage); // Debugging

        clearUI(); // Kosongkan UI sebelum update

        ImageView[] imageViews = {ImageView1, ImageView2, ImageView3, ImageView4, ImageView5, ImageView6};
        Button[] viewButtons = {viewItem1, viewItem2, viewItem3, viewItem4, viewItem5, viewItem6};

        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, lostItems.size());

        //System.out.println("Displaying items from index " + startIndex + " to " + (endIndex - 1)); // Debugging

        for (int i = startIndex, slot = 0; i < endIndex; i++, slot++) {
            LostItem item = lostItems.get(i);
            String imagePath = item.getImagePath();

            imageViews[slot].setFitWidth(128);
            imageViews[slot].setFitHeight(174);
            imageViews[slot].setPreserveRatio(false);

            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image("file:" + imagePath,
                        imageViews[slot].getFitWidth(),
                        imageViews[slot].getFitHeight(),
                        false,
                        true
                );
                imageViews[slot].setImage(image);
            }

            // Buat gambar bulat di hujung
            applyRoundedClip(imageViews[slot], 20, 20);

            // Tetapkan tindakan butang
            int index = i;
            viewButtons[slot].setOnAction(e -> showItemDetails(lostItems.get(index)));

            // Pastikan UI ditunjukkan
            imageViews[slot].setVisible(true);
            viewButtons[slot].setVisible(true);
        }

    }

    private void clearUI() {
        ImageView[] imageViews = {ImageView1, ImageView2, ImageView3, ImageView4, ImageView5, ImageView6};
        Button[] viewButtons = {viewItem1, viewItem2, viewItem3, viewItem4, viewItem5, viewItem6};

        for (int i = 0; i < ITEMS_PER_PAGE; i++) {
            imageViews[i].setImage(null);
            imageViews[i].setVisible(false);
            viewButtons[i].setVisible(false);
        }
    }

    private void applyRoundedClip(ImageView imageView, double arcWidth, double arcHeight) {
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcWidth(arcWidth);
        clip.setArcHeight(arcHeight);
        imageView.setClip(clip);
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
    public void handleNext() {
        if ((currentPage + 1) * ITEMS_PER_PAGE < lostItems.size()) {
            currentPage++;
            //System.out.println("Next Page Triggered! New Page: " + currentPage);
            updateUI();
        } else {
            //System.out.println("Next Page Not Triggered (Already Last Page)");
        }
    }

    @FXML
    public void handlePrev() {
        if (currentPage > 0) {
            currentPage--;
            //System.out.println("Previous Page Triggered! New Page: " + currentPage);
            updateUI();
        } else {
            //System.out.println("Previous Page Not Triggered (Already First Page)");
        }
    }

    @FXML
    private void openReportWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/TrackBack/ReportFoundItem.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Report Found Item");
            stage.setScene(new Scene(root));
            stage.show();

            // 🚪 Close the choice window
            Stage choiceStage = (Stage) buttonReport.getScene().getWindow();
            choiceStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close the application?");

        // Set default OK and Cancel buttons
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        // Show the alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit(); // Close the application
        }
    }



}
