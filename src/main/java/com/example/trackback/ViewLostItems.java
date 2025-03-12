package com.example.trackback;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewLostItems {

    @FXML
    private Label lblInfo;

    @FXML
    public void initialize() {
        lblInfo.setText("Lost items will appear here!");
    }
}
