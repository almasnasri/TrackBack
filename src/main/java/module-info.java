module com.example.trackback {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.trackback to javafx.fxml;
    exports com.example.trackback;
    exports database;
}
