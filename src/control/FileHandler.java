


package control;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FileHandler {
    private final DatabaseConnector dbConnector;

    public FileHandler() {
        dbConnector = new DatabaseConnector();
    }

    // Save transcript to the database
    public void saveTranscript(String studentId, double sgpa, double cgpa) {
        String query = "INSERT INTO transcripts (student_id, sgpa, cgpa) VALUES (?, ?, ?)";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            statement.setDouble(2, sgpa);
            statement.setDouble(3, cgpa);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
