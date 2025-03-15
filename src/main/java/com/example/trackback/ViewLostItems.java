/*package com.example.trackback;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewLostItems {

    @FXML
    private Label lblInfo;

    @FXML
    public void initialize() {
        lblInfo.setText("Lost items will appear here!");
    }
}*/

package com.example.trackback;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewLostItems {

    @FXML
    private TableView<LostItem> tableView;

    @FXML
    private TableColumn<LostItem, String> itemNameColumn;

    @FXML
    private TableColumn<LostItem, String> locationColumn;

    @FXML
    private TableColumn<LostItem, String> dateColumn;

    private ObservableList<LostItem> lostItemsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadLostItems();
    }

    private void loadLostItems() {
        lostItemsList.clear();
        try (Connection connection = DatabaseConnection.getConnection("reportfounditem");
             PreparedStatement statement = connection.prepareStatement("SELECT item_name, location, date FROM lost_items");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                lostItemsList.add(new LostItem(
                        resultSet.getString("item_name"),
                        resultSet.getString("location"),
                        resultSet.getString("date")
                ));
            }
            tableView.setItems(lostItemsList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

