/*package com.example.trackback;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

import database.DatabaseConnection;


public class ReportFoundItem {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhonenum;

    @FXML
    private TextField txtLocation;

    @FXML
    private DatePicker dateFound;

    @FXML
    private ComboBox<String> comboCategory;

    @FXML
    private Button btnUpload;

    @FXML
    private ImageView imgItem;

    @FXML
    private Button btnSubmit;


    @FXML
    private void handleUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imgItem.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void handleSubmit() {
        if (txtName.getText().isEmpty() ||
                txtPhonenum.getText().isEmpty() ||
                txtLocation.getText().isEmpty() ||
                dateFound.getValue() == null ||
                comboCategory.getValue() == null ||
                imgItem.getImage() == null) {

            // Show error message if any field is empty
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Form Incomplete");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please fill in all fields before submitting the report.");
            errorAlert.showAndWait();

        } else {
            // Show confirmation message
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Submission");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure to submit lost item's report?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Handle successful submission
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Submission Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Lost item report submitted successfully!");
                successAlert.showAndWait();
            }
        }
    }

}*/

package com.example.trackback;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import database.DatabaseConnection;

public class ReportFoundItem {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhonenum;

    @FXML
    private TextField txtLocation;

    @FXML
    private DatePicker dateFound;

    @FXML
    private ComboBox<String> comboCategory;

    @FXML
    private Button btnUpload;

    @FXML
    private ImageView imgItem;

    @FXML
    private Button btnSubmit;

    private File selectedImageFile; // To store the selected image file

    @FXML
    private void handleUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            imgItem.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void handleSubmit() {
        if (txtName.getText().isEmpty() ||
                txtPhonenum.getText().isEmpty() ||
                txtLocation.getText().isEmpty() ||
                dateFound.getValue() == null ||
                comboCategory.getValue() == null ||
                imgItem.getImage() == null) {

            // Show error message if any field is empty
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Form Incomplete");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please fill in all fields before submitting the report.");
            errorAlert.showAndWait();
            return;
        }

        // Show confirmation message
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Submission");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to submit the found item's report?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Convert date to string format
            LocalDate foundDate = dateFound.getValue();
            String formattedDate = foundDate.toString(); // Save as "YYYY-MM-DD"

            // Call method to insert data into the database
            addFoundItem(txtName.getText(), txtPhonenum.getText(), txtLocation.getText(), formattedDate, comboCategory.getValue());

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Submission Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Found item report submitted successfully!");
            successAlert.showAndWait();
        }
    }

    private void addFoundItem(String name, String phone, String location, String date, String category) {
        String query = "INSERT INTO found_items (name, phone, location, date_found, category) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection("reportfounditem");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, location);
            statement.setString(4, date);
            statement.setString(5, category);
            statement.executeUpdate();

            System.out.println("✅ Item added to reportfounditem database.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to insert into reportfounditem database!");
            e.printStackTrace();
        }
    }
}

