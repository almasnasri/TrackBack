package com.example.trackback;

import database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    // ❌ Handle Close Button (X) Click

    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setOnCloseRequest(event -> returnToLogin());
        });
    }


    @FXML
    private void registerUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            //messageLabel.setText("❌ Please fill in all fields!");
            showMessageBox("Error","❌ Please fill in all fields!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection("login")) {
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                //messageLabel.setText("⚠️ Username already exists!");
                showMessageBox("Error","⚠️ Username already exists! Choose another username");
                return;
            }

            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();

            showMessageBox("Successful Registration","✅ Registration Successful! Login again at login page");
            // ✅ Close Register Window and Reopen Login
            returnToLogin();

        } catch (SQLException e) {
            showMessageBox("Database Error","❌ Database Error!");
            e.printStackTrace();
        }
    }
    private void showMessageBox(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 🔄 Method to Return to Login
    private void returnToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/TrackBack/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();

            // 🚪 Close Register Window
            ((Stage) usernameField.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
