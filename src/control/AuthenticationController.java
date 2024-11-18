package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationController {
    private final DatabaseConnector dbConnector;

    public AuthenticationController() {
        dbConnector = new DatabaseConnector();
    }

    // Authenticate registry user
    public boolean authenticateRegistry(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = 'registry'";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Authenticate student
    public boolean authenticateStudent(String studentId) {
        String query = "SELECT * FROM users WHERE username = ? AND role = 'student'";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Authentication successful for student ID: " + studentId);
                return true;
            } else {
                System.out.println("No matching student found for ID: " + studentId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error while authenticating student ID: " + studentId);
            e.printStackTrace();
            return false;
        }
    }
}
