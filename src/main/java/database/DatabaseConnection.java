/*package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/login";
    private static final String USER = "root";
    private static final String PASSWORD = "BellaNabila05_";

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
}*/

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL_TEMPLATE = "jdbc:mysql://localhost:3306/%s";
    private static final String USER = "root";
    private static final String PASSWORD = "BellaNabila05_";

    public static Connection getConnection(String databaseName) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = String.format(URL_TEMPLATE, databaseName);
            connection = DriverManager.getConnection(dbURL, USER, PASSWORD);
            System.out.println("✅ Connected to database: " + databaseName);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Database Connection Failed for " + databaseName);
            e.printStackTrace();
        }
        return connection;
    }
}
