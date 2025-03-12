module com.example.trackback {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;  // Add this line
    requires java.sql;

    opens com.example.trackback to javafx.fxml;
    exports com.example.trackback;
    exports database;
}
