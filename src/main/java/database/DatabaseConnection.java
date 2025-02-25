package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/trackback_db";
    private static final String USER = "root";
    private static final String PASSWORD = "IMFA@almas";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database Connected Successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Database Connection Failed!");
            e.printStackTrace();
        }
        return connection;
    }
}
