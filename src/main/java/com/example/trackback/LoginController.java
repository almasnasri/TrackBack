package com.example.trackback;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showMessageDialog("Error", "❌ Please fill in all fields!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection("login")) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                showMessageDialog("Success", "✅ Login Successful!");
                openDashboard();
                openChoiceWindow();
            } else {
                showMessageDialog("Error", "❌ Invalid Username or Password!");
            }
        } catch (SQLException e) {
            showMessageDialog("Database Error", "❌ Database Error!");
            e.printStackTrace();
        }
    }

    private void showMessageDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openDashboard() {
        System.out.println("User logged in, redirecting...");
    }

    @FXML
    public void openRegisterWindow() {
        try {
            URL fxmlLocation = getClass().getResource("/com/example/TrackBack/register.fxml");

            if (fxmlLocation == null) {
                System.out.println("❌ Error: FXML file 'register.fxml' not found!");
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.show();
            ((Stage) usernameField.getScene().getWindow()).close();

        } catch (IOException e) {
            System.out.println("❌ Error loading 'register.fxml'!");
            e.printStackTrace();
        }
    }

    @FXML
    private void openChoiceWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/TrackBack/choice.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Choice");
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) usernameField.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
