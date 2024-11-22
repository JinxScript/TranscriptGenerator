package control;

import model.Module;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistryController {
    private final DatabaseConnector dbConnector;

    public RegistryController() {
        dbConnector = new DatabaseConnector();
    }

    // Fetch all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                students.add(new Student(
                        resultSet.getString("full_name"),
                        resultSet.getString("programme"),
                        resultSet.getInt("year_of_study"),
                        resultSet.getString("student_id"),
                        resultSet.getDate("date_of_birth").toString()
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    // Find a student by ID
    public Student findStudentById(String studentId) {
        String query = "SELECT * FROM students WHERE student_id = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Student(
                        resultSet.getString("full_name"),
                        resultSet.getString("programme"),
                        resultSet.getInt("year_of_study"),
                        resultSet.getString("student_id"),
                        resultSet.getDate("date_of_birth").toString()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error finding student: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Fetch all modules for a specific student
   public List<Module> getModulesByStudentId(String studentId) {
    List<Module> modules = new ArrayList<>();
    String query = "SELECT m.module_code, m.module_name, m.credits, m.year, m.semester, sm.module_mark " +
                   "FROM modules m " +
                   "JOIN student_modules sm ON m.module_id = sm.module_id " +
                   "WHERE sm.student_id = ?";
    try (Connection connection = dbConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, studentId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            modules.add(new Module(
                    resultSet.getString("module_code"),
                    resultSet.getString("module_name"),
                    resultSet.getDouble("module_mark"),
                    resultSet.getInt("credits"),
                    resultSet.getInt("year"),
                    resultSet.getInt("semester")
            ));
        }
    } catch (SQLException e) {
        System.out.println("Error fetching modules: " + e.getMessage());
        e.printStackTrace();
    }
    return modules;
}

}
