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

    private File selectedImageFile; // Store the selected image file

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

    private void clearFields() {
        txtName.clear();
        txtPhonenum.clear();
        txtLocation.clear();
        dateFound.setValue(null);
        comboCategory.setValue(null);
        imgItem.setImage(null);
        selectedImageFile = null;
    }

    private void switchToChoiceController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/TrackBack/choice.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleSubmit() {
        if (txtName.getText().isEmpty() ||
                txtPhonenum.getText().isEmpty() ||
                txtLocation.getText().isEmpty() ||
                dateFound.getValue() == null ||
                comboCategory.getValue() == null ||
                selectedImageFile == null) {

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Form Incomplete");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please fill in all fields before submitting the report.");
            errorAlert.showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Submission");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to submit the found item's report?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            LocalDate foundDate = dateFound.getValue();
            String formattedDate = foundDate.toString(); // Format YYYY-MM-DD

            String imagePath = selectedImageFile.getAbsolutePath(); // Get image file path

            addFoundItem(txtName.getText(), txtPhonenum.getText(), txtLocation.getText(), formattedDate, comboCategory.getValue(), imagePath);

            // Show "Report another item?" alert
            Alert nextActionAlert = new Alert(Alert.AlertType.CONFIRMATION);
            nextActionAlert.setTitle("Submission Successful");
            nextActionAlert.setHeaderText(null);
            nextActionAlert.setContentText("Found item report submitted successfully! Do you want to report another item?");

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            nextActionAlert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> nextAction = nextActionAlert.showAndWait();
            if (nextAction.isPresent() && nextAction.get() == yesButton) {
                clearFields(); // Reset fields for new input
            } else {
                switchToChoiceController(); // Go back to ChoiceController interface
            }

        }
    }

    private void addFoundItem(String name, String phone, String location, String date, String category, String imagePath) {
        String query = "INSERT INTO founditems (Name, phone_number, location_found, date_found, category, image_path) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection("reportfounditem");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, location);
            statement.setString(4, date);
            statement.setString(5, category);
            statement.setString(6, imagePath);

            statement.executeUpdate();

            System.out.println("✅ Item added to reportfounditem database.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to insert into reportfounditem database!");
            e.printStackTrace();
        }

    }
}