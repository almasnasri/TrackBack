package com.example.trackback;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            messageLabel.setText("❌ Please fill in all fields!");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                messageLabel.setText("✅ Login Successful!");
                messageLabel.setStyle("-fx-text-fill: green;");
                openDashboard();
            } else {
                messageLabel.setText("❌ Invalid Username or Password!");
                messageLabel.setStyle("-fx-text-fill: red;");
            }

        } catch (SQLException e) {
            messageLabel.setText("❌ Database Error!");
            e.printStackTrace();
        }
    }

    private void openDashboard() {
        System.out.println("User logged in, redirecting...");
    }

    @FXML
    public void openRegisterWindow() {
        try {
            URL fxmlLocation = getClass().getResource("register.fxml");

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
        } catch (IOException e) {
            System.out.println("❌ Error loading 'register.fxml'!");
            e.printStackTrace();
        }
    }
}
