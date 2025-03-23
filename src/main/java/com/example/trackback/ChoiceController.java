package com.example.trackback;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ChoiceController {

    @FXML
    private Button btnChoiceView;

    @FXML
    private Button btnChoiceReport;

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
            Stage choiceStage = (Stage) btnChoiceReport.getScene().getWindow();
            choiceStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openViewWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/TrackBack/ViewLostItems.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("View Lost Items");
            stage.setScene(new Scene(root));
            stage.show();

            // 🚪 Close the choice window
            Stage choiceStage = (Stage) btnChoiceView.getScene().getWindow();
            choiceStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
