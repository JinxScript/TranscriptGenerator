package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studenttranscriptgenerator";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Constructor to load the JDBC driver
    public DatabaseConnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    // Method to provide a new connection
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
            return null;
        }
    }
}
